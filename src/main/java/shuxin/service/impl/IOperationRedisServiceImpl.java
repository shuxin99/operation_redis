package shuxin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.util.Pool;
import shuxin.common.R;
import shuxin.config.RedisClient;
import shuxin.config.RedisType;
import shuxin.config.business.BusinessConfig;
import shuxin.constant.BusinessConstant;
import shuxin.entity.TupleVO;
import shuxin.service.IOperationRedisService;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IOperationRedisServiceImpl implements IOperationRedisService {

    @Resource
    private RedisClient redisClient;

    @Resource
    private BusinessConfig businessConfig;

    @Override
    public String scanData4Redis(String match, String writeFileFlag, String type, String deleteFlag, Integer jedisNumber, boolean bigKeyFlag) {
        //初始下标
        String course = "0";
        Long count = 0L;
        ScanParams scanParams = new ScanParams();
        scanParams.count(businessConfig.getScanCount());
        scanParams.match(match);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String dateFormatter = LocalDateTime.now().format(dateTimeFormatter);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(match.replace("*", "") + "-" + type + "-" + dateFormatter, true))) {
            if (redisClient.getType() == RedisType.Standalone) {
                count = scanDataByStandLone(writer, writeFileFlag, count, course, scanParams, type, deleteFlag, bigKeyFlag);
            } else if (redisClient.getType() == RedisType.Sentinel) {
                count = scanDataBySentinel(writer, writeFileFlag, count, course, scanParams, type, deleteFlag, bigKeyFlag);
            } else if (redisClient.getType() == RedisType.Cluster) {
                count = scanDataByCluster(writer, writeFileFlag, count, course, scanParams, type, deleteFlag, bigKeyFlag, jedisNumber);
            } else {
                throw new RuntimeException("redisClient实例初始化失败");
            }
            return new R<Long>().success(count);
        } catch (Exception e) {
            log.error("scanData4Redis出异常啦", e);
        }
        log.info("scanData4Redis失败");
        return new R<Long>().fail(count);
    }

    @Override
    public String repairData2Redis(String fileUrl, String type, boolean bigKeyFlag) {
        Long count = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileUrl))) {
            String line;
            ArrayList<String> arrayList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                arrayList.add(line);
                if (arrayList.size() == businessConfig.getRepairCountEveryTime()) {
                    count = this.repairDataByType(type, arrayList, count, bigKeyFlag);
                    arrayList.clear();
                }
                //终止流程
                if (endRepairProgram(count)) {
                    return new R<Long>().success(count);
                }
            }
            //处理最后不足1000条数据
            if (arrayList.size() < businessConfig.getRepairCountEveryTime()) {
                count = this.repairDataByType(type, arrayList, count, bigKeyFlag);
            }
            log.info("修复数据成功,修复数据:{}条", count);
            return new R<Long>().success(count);
        } catch (Exception e) {
            log.error("修复数据异常", e);
        }
        log.info("修复数据失败");
        return new R<Long>().fail(count);
    }


    @Override
    public String changeSwitchInfo(String cleanSwitch, String repairSwitch) {
        try {
            if (StringUtils.equals(BusinessConstant.Y, cleanSwitch)) {
                redisClient.set(BusinessConstant.CLEAN_REDIS_DATA_BREAK_SWITCH, BusinessConstant.Y);
            } else {
                redisClient.set(BusinessConstant.CLEAN_REDIS_DATA_BREAK_SWITCH, BusinessConstant.N);
            }
            if (StringUtils.equals(BusinessConstant.Y, repairSwitch)) {
                redisClient.set(BusinessConstant.REPAIR_REDIS_DATA_BREAK_SWITCH, BusinessConstant.Y);
            } else {
                redisClient.set(BusinessConstant.REPAIR_REDIS_DATA_BREAK_SWITCH, BusinessConstant.N);
            }
            log.info("传入跳出清理流程开关标识:{}, 跳出修复数据流程开关标识:{},changeSwitchInfo", cleanSwitch, repairSwitch);
            return new R<>().success();
        } catch (Exception e) {
            log.error("传入跳出清理流程开关标识:{}, 跳出修复数据流程开关标识:{},changeSwitchInfo", cleanSwitch, repairSwitch, e);
        }
        return new R<>().fail();
    }

    private Long scanDataBySentinel(BufferedWriter writer, String writeFileFlag, Long count, String course, ScanParams scanParams, String type, String deleteFlag, boolean bigKeyFlag) throws IOException {
        Map<String, Pool<Jedis>> nodes = redisClient.getNodes();
        Set<Map.Entry<String, Pool<Jedis>>> entries = nodes.entrySet();
        for (Map.Entry<String, Pool<Jedis>> entry : entries) {
            Jedis jedis = entry.getValue().getResource();
            if (!jedis.info("replication").contains("role:slave")) {
                do {
                    ScanResult<String> result = jedis.scan(course, scanParams);
                    course = result.getCursor();
                    List<String> list = result.getResult();
                    //不同结构类型进行不同处理
                    count = this.handleDataByMultiType(writeFileFlag, count, writer, list, type, deleteFlag, bigKeyFlag);
                } while (Long.parseLong(course) != 0L);
            }
            //注意释放连接
            jedis.close();
        }
        return count;
    }

    private Long scanDataByStandLone(BufferedWriter writer, String writeFileFlag, Long count, String course, ScanParams scanParams, String type, String deleteFlag, boolean bigKeyFlag) throws IOException {
        do {
            ScanResult<String> result = redisClient.scan(course, scanParams);
            course = result.getCursor();
            List<String> list = result.getResult();
            //不同结构类型进行不同处理
            count = this.handleDataByMultiType(writeFileFlag, count, writer, list, type, deleteFlag, bigKeyFlag);
        } while (Long.parseLong(course) != 0L);
        return count;
    }


    private Long scanDataByCluster(BufferedWriter writer, String writeFileFlag, Long count, String course, ScanParams params, String type, String deleteFlag, boolean bigKeyFlag, Integer jedisNumber) throws IOException {
        Map<String, JedisPool> clusterNodes = redisClient.getClusterNodes();
        Set<Map.Entry<String, JedisPool>> entries = clusterNodes.entrySet();
        log.info("scanData4Redis,redis集群的节点个数:{}", clusterNodes.size());
        if (jedisNumber == null) {
            jedisNumber = 1;
        }
        int countNode = 0;
        for (Map.Entry<String, JedisPool> entry : entries) {
            countNode += 1;
            if (countNode < jedisNumber) {
                log.info("该节点已经处理过,entry-key:{}", entry.getKey());
                continue;
            }
            boolean resetFlag = true;
            do {
                try (Jedis jedis = entry.getValue().getResource()) {
                    //集群模式下只在主节点进行scan扫描
                    if (!jedis.info("replication").contains("role:slave")) {
                        int scanNumber = 0;
                        do {
                            ScanResult<String> result = jedis.scan(course, params);
                            scanNumber++;
                            course = result.getCursor();
                            List<String> list = result.getResult();
                            if (!CollectionUtils.isEmpty(list)) {
                                //不同结构类型进行不同处理
                                count = this.handleDataByMultiType(writeFileFlag, count, writer, list, type, deleteFlag, bigKeyFlag);
                                //终止流程
                                if (endProgram(count, countNode)) {
                                    return count;
                                }
                            }
                            //休眠
                            sleepProgram(scanNumber);
                        } while (Long.parseLong(course) != 0L);
                    }
                    resetFlag = false;
                } catch (JedisConnectionException e) {
                    resetFlag = true;
                    log.error("jedis连接异常", e);
                }
            } while (resetFlag);
            log.info("即将遍历下一个节点，当前节点：{}已遍历完成", entry.getKey());
        }
        return count;
    }

    private void sleepProgram(int scanNumber) {
        if (scanNumber % businessConfig.getScanNumberLimit() == 0) {
            try {
                Thread.sleep(businessConfig.getSleepMillis());
            } catch (InterruptedException e) {
                log.error("sleepProgram异常", e);
            }
        }
    }

    private boolean endProgram(Long count, int countNode) {
        String breakFlag = redisClient.get("CLEAN_REDIS_DATA_BREAK_SWITCH");
        if (StringUtils.equals("Y", breakFlag)) {
            log.info("跳出清理流程，处理的jedis节点序号:{},处理数据条数:{}", countNode, count);
            return true;
        }
        return false;
    }

    private boolean endRepairProgram(Long count) {
        String breakFlag = redisClient.get("REPAIR_REDIS_DATA_BREAK_SWITCH");
        if (StringUtils.equals("Y", breakFlag)) {
            log.info("跳出修复流程,处理数据条数:{}", count);
            return true;
        }
        return false;
    }

    @Override
    public Long handleDataByMultiType(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String type, String deleteFlag, boolean bigKeyFlag) throws IOException {
        if (Boolean.FALSE.equals(bigKeyFlag)) {
            count = routeHandleDataNonBigKey(writeFileFlag, count, writer, list, type, deleteFlag);
        } else {
            count = routeHandleDataBigKey(writeFileFlag, count, writer, list, type, deleteFlag, bigKeyFlag);
        }
        return count;
    }

    private Long routeHandleDataBigKey(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String type, String deleteFlag, boolean bigKeyFlag) throws IOException {
        switch (type) {
            case "string":
                count = getDataByString(writeFileFlag, count, writer, list, deleteFlag);
                break;
            case "hash":
                count = getDataByHash(writeFileFlag, count, writer, list, deleteFlag, bigKeyFlag);
                break;
            case "set":
                count = getDataBySet(writeFileFlag, count, writer, list, deleteFlag, bigKeyFlag);
                break;
            case "list":
                count = getDataByList(writeFileFlag, count, writer, list, deleteFlag, bigKeyFlag);
                break;
            case "zset":
                count = getDataByZSet(writeFileFlag, count, writer, list, deleteFlag, bigKeyFlag);
                break;
            default:
                break;
        }
        return count;
    }

    private Long routeHandleDataNonBigKey(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String type, String deleteFlag) throws IOException {
        switch (type) {
            case "string":
                count = getDataByString(writeFileFlag, count, writer, list, deleteFlag);
                break;
            case "hash":
                count = getDataByHash(writeFileFlag, count, writer, list, deleteFlag);
                break;
            case "set":
                count = getDataBySet(writeFileFlag, count, writer, list, deleteFlag);
                break;
            case "list":
                count = getDataByList(writeFileFlag, count, writer, list, deleteFlag);
                break;
            case "zset":
                count = getDataByZSet(writeFileFlag, count, writer, list, deleteFlag);
                break;
            default:
                break;
        }
        return count;
    }


    private Long getDataByString(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String deleteFlag) throws IOException {
        for (String key : list) {
            String value = redisClient.get(key);
            count++;
            if (Boolean.parseBoolean(writeFileFlag)) {
                String line = key + "||" + value;
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            if (Boolean.parseBoolean(deleteFlag)) {
                redisClient.del(key);
            }
        }
        return count;
    }


    private Long getDataByHash(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String deleteFlag) throws IOException {
        for (String key : list) {
            Map<String, String> stringStringMap = redisClient.hgetAll(key);
            count++;
            if (Boolean.parseBoolean(writeFileFlag)) {
                String line = key + "||" + JSON.toJSONString(stringStringMap);
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            if (Boolean.parseBoolean(deleteFlag)) {
                redisClient.del(key);
            }
        }
        return count;
    }

    private Long getDataByHash(String writeFileFlag, Long count, BufferedWriter writer, List<String> keyList, String deleteFlag, boolean bigKeyFlag) throws IOException {
        for (String key : keyList) {
            String cursor = "0";
            long scanNumber = 0;
            do {
                ScanResult<Map.Entry<String, String>> result = redisClient.hscan(key, cursor, new ScanParams().count(businessConfig.getHScanCount()));
                scanNumber++;
                cursor = result.getCursor();
                List<Map.Entry<String, String>> list = result.getResult();
                if (scanNumber % businessConfig.getHScanNumberLimit() == 0) {
                    try {
                        Thread.sleep(businessConfig.getHSleepMillis());
                    } catch (InterruptedException e) {
                        log.error("scanData4Redis-getDataByHash出异常了", e);
                    }
                }
                if (CollectionUtils.isEmpty(list)) {
                    continue;
                }
                for (Map.Entry<String, String> entry : list) {
                    if (Boolean.parseBoolean(writeFileFlag)) {
                        writer.write(key + "||" + JSON.toJSONString(entry));
                        writer.newLine();
                        writer.flush();
                    }
                    //删除每个field
                    if (Boolean.parseBoolean(deleteFlag)) {
                        redisClient.hdel(key, entry.getKey());
                    }
                    count++;
                }
            } while (!StringUtils.equals("0", cursor));
        }
        return count;
    }


    private Long getDataBySet(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String deleteFlag) throws IOException {
        for (String key : list) {
            Set<String> smembers = redisClient.smembers(key);
            count++;
            if (Boolean.parseBoolean(writeFileFlag)) {
                String line = key + "||" + JSON.toJSONString(smembers);
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            if (Boolean.parseBoolean(deleteFlag)) {
                redisClient.del(key);
            }
        }
        return count;
    }


    private Long getDataBySet(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String deleteFlag, boolean bigKeyFlag) throws IOException {
        for (String key : list) {
            String cursor = "0";
            long scanNumber = 0L;
            do {
                ScanResult<String> scanResult = redisClient.sscan(key, cursor, new ScanParams().count(businessConfig.getSScanCount()));
                scanNumber++;
                cursor = scanResult.getCursor();
                List<String> result = scanResult.getResult();
                if (scanNumber % businessConfig.getSScanNumberLimit() == 0) {
                    try {
                        Thread.sleep(businessConfig.getSSleepMillis());
                    } catch (InterruptedException e) {
                        log.error("scanData4Redis-getDataBySet出异常了", e);
                    }
                }
                if (CollectionUtils.isEmpty(result)) {
                    continue;
                }
                for (String member : result) {
                    count++;
                    if (Boolean.parseBoolean(writeFileFlag)) {
                        String line = key + "||" + JSON.toJSONString(member);
                        writer.write(line);
                        writer.newLine();
                        writer.flush();
                    }
                    if (Boolean.parseBoolean(deleteFlag)) {
                        redisClient.srem(key, member);
                    }
                }
            } while (!StringUtils.equals("0", cursor));
            log.info("该key为:[{}],大小为:[{}]", key, redisClient.scard(key));
        }
        return count;
    }


    private Long getDataByList(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String deleteFlag) throws IOException {
        for (String key : list) {
            List<String> lrange = redisClient.lrange(key, 0, -1);
            count++;
            if (Boolean.parseBoolean(writeFileFlag)) {
                String line = key + "||" + JSON.toJSONString(lrange);
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            if (Boolean.parseBoolean(deleteFlag)) {
                redisClient.del(key);
            }
        }
        return count;
    }

    private Long getDataByList(String writeFileFlag, Long count, BufferedWriter writer, List<String> keyList, String deleteFlag, boolean bigKeyFlag) throws IOException {
        for (String key : keyList) {
//            List<String> list = redisClient.lrange(key, 0, -1);
//            count++;
//            Long num = 0;
//            for (String l : list) {
//                if (Boolean.parseBoolean(writeFileFlag)) {
////                    String line = key + "||" + l + "\r\n";
////                String line = key + " " + JSON.toJSONString(stringStringMap) + "\n";
//                    String line = key + "||" + l;
//                    writer.write(line);
//                    writer.newLine();
//                    writer.flush();
//                }
//                num++;
//            }
            Long num = redisClient.llen(key);
            long s = 0;
            long e = 0;
            while (s < num) {
                //计算结束下标
                e = s + 99;
                List<String> list = redisClient.lrange(key, s, e);
                //计算开始下标
                s = e + 1;
                for (String l : list) {
                    if (Boolean.parseBoolean(writeFileFlag)) {
                        String line = key + "||" + JSON.toJSONString(l);
                        writer.write(line);
                        writer.newLine();
                        writer.flush();
                    }
                    count++;
                }
            }
            log.info("该key:{}有{}个成员", key, num);
            if (Boolean.parseBoolean(deleteFlag)) {
                long start = 0;
                long end = -101;
                while (redisClient.llen(key) != 0) {
                    //每次删除最后的一百个
                    redisClient.ltrim(key, start, end);
                    end -= 100;
                }
            }
        }
        return count;
    }

    private Long getDataByZSet(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String deleteFlag) throws IOException {
        for (String key : list) {
            Set<Tuple> tuples = redisClient.zrangeWithScores(key, 0, -1);
            Set<TupleVO> collect = tuples.stream().map(tuple -> {
                TupleVO tupleVO = new TupleVO();
                tupleVO.setElement(tuple.getElement());
                tupleVO.setScore(tuple.getScore());
                return tupleVO;
            }).collect(Collectors.toSet());
            count++;
            if (Boolean.parseBoolean(writeFileFlag)) {
                String line = key + "||" + JSON.toJSONString(collect);
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            if (Boolean.parseBoolean(deleteFlag)) {
                redisClient.del(key);
            }
        }
        return count;
    }

    private Long getDataByZSet(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String deleteFlag, boolean bigKeyFlag) throws IOException {
        for (String key : list) {
            String cursor = "0";
            long scanNumber = 0L;
            do {
                ScanResult<Tuple> scanResult = redisClient.zscan(key, cursor, new ScanParams().count(businessConfig.getZScanCount()));
                scanNumber++;
                cursor = scanResult.getCursor();
                List<Tuple> tupleList = scanResult.getResult();
                if (scanNumber % businessConfig.getZScanNumberLimit() == 0) {
                    try {
                        Thread.sleep(businessConfig.getZSleepMillis());
                    } catch (InterruptedException e) {
                        log.error("scanData4Redis-getDataByZSet出异常了", e);
                    }
                }
                if (CollectionUtils.isEmpty(tupleList)) {
                    continue;
                }
                Set<TupleVO> collect = tupleList.stream().map(tuple -> {
                    TupleVO tupleVO = new TupleVO();
                    tupleVO.setElement(tuple.getElement());
                    tupleVO.setScore(tuple.getScore());
                    return tupleVO;
                }).collect(Collectors.toSet());
                for (TupleVO tupleVO : collect) {
                    count++;
                    if (Boolean.parseBoolean(writeFileFlag)) {
                        String line = key + "||" + JSON.toJSONString(tupleVO);
                        writer.write(line);
                        writer.newLine();
                        writer.flush();
                    }
                    if (Boolean.parseBoolean(deleteFlag)) {
                        redisClient.zrem(key, tupleVO.getElement());
                    }
                }
            } while (!StringUtils.equals("0", cursor));
            log.info("key:[{}],该key的元素个数:[{}]", key, redisClient.zcard(key));
        }
        return count;
    }


    @Override
    public Long repairDataByType(String type, ArrayList<String> arrayList, Long count, boolean bigKeyFlag) {
        if (Boolean.FALSE.equals(bigKeyFlag)) {
            count = routeRepairNonBigKey(type, arrayList, count);
        } else {
            count = routeRepairBigKey(type, arrayList, count, bigKeyFlag);
        }
        return count;
    }

    private Long routeRepairBigKey(String type, ArrayList<String> arrayList, Long count, boolean bigKeyFlag) {
        switch (type) {
            case "string":
                count = repairDataByString(arrayList, count);
                break;
            case "hash":
                count = repairDataByHash(arrayList, count, bigKeyFlag);
                break;
            case "set":
                count = repairDataBySet(arrayList, count, bigKeyFlag);
                break;
            case "list":
                count = repairDataByList(arrayList, count, bigKeyFlag);
                break;
            case "zset":
                count = repairDataByZSet(arrayList, count, bigKeyFlag);
                break;
            default:
                break;
        }
        return count;
    }

    private Long routeRepairNonBigKey(String type, ArrayList<String> arrayList, Long count) {
        switch (type) {
            case "string":
                count = repairDataByString(arrayList, count);
                break;
            case "hash":
                count = repairDataByHash(arrayList, count);
                break;
            case "set":
                count = repairDataBySet(arrayList, count);
                break;
            case "list":
                count = repairDataByList(arrayList, count);
                break;
            case "zset":
                count = repairDataByZSet(arrayList, count);
                break;
            default:
                break;
        }
        return count;
    }

    private Long repairDataByString(ArrayList<String> arrayList, Long count) {

        for (String line : arrayList) {
            String[] arr = line.split("\\|\\|");
            String key = arr[0];
            String value = arr[1];
            redisClient.set(key, value);
            count++;
        }
        return count;
    }

    private Long repairDataByHash(ArrayList<String> arrayList, Long count) {
        for (String line : arrayList) {
            String[] arr = line.split("\\|\\|");
            String key = arr[0];
            String value = arr[1];
            //注意格式转换，使用字节码可能会转换失败
            Map<String, String> map = JSONObject.parseObject(value, new TypeReference<Map<String, String>>() {
            });
            redisClient.hmset(key, map);
            count++;
        }
        return count;
    }

    private Long repairDataByHash(ArrayList<String> arrayList, Long count, boolean bigKeyFlag) {
        for (String line : arrayList) {
            String[] arr = line.split("\\|\\|");
            String key = arr[0];
            String value = arr[1];
            //注意格式转换，使用字节码可能会转换失败
            String replacedStr = value.replace(":\"{", ":{").replace("}\"}", "}}");
            Map<String, String> map = JSONObject.parseObject(replacedStr, new TypeReference<Map<String, String>>() {
            });
            redisClient.hmset(key, map);
            count++;
        }
        return count;
    }

    private Long repairDataBySet(ArrayList<String> arrayList, Long count) {
        for (String line : arrayList) {
            String[] arr = line.split("\\|\\|");
            String key = arr[0];
            String value = arr[1];
            Set<String> strings = JSON.parseObject(value, new TypeReference<Set<String>>() {
            });
            for (String member : strings) {
                redisClient.sadd(key, member);
            }
            count++;
        }
        return count;
    }

    private Long repairDataBySet(ArrayList<String> arrayList, Long count, boolean bigKeyFlag) {
        for (String line : arrayList) {
            String[] arr = line.split("\\|\\|");
            String key = arr[0];
            String value = arr[1];
            String member = JSON.parseObject(value, new TypeReference<String>() {
            });
            redisClient.sadd(key, member);
            count++;
        }
        return count;
    }

    private Long repairDataByList(ArrayList<String> arrayList, Long count) {

        for (String line : arrayList) {
            String[] arr = line.split("\\|\\|");
            String key = arr[0];
            String value = arr[1];
            List<String> list = JSONObject.parseObject(value, new TypeReference<List<String>>() {
            });
            for (String l : list) {
                redisClient.rpush(key, l);
            }
            count++;
        }
        return count;
    }

    private Long repairDataByList(ArrayList<String> arrayList, Long count, boolean bigKeyFlag) {
        for (String line : arrayList) {
            String[] arr = line.split("\\|\\|");
            String key = arr[0];
            String value = arr[1];
            String s = JSONObject.parseObject(value, new TypeReference<String>() {
            });
            redisClient.rpush(key, s);
            count++;
        }
        return count;
    }

    private Long repairDataByZSet(ArrayList<String> arrayList, Long count) {

        for (String line : arrayList) {
            String[] arr = line.split("\\|\\|");
            String key = arr[0];
            String value = arr[1];
            Set<TupleVO> tuples = JSONObject.parseObject(value, new TypeReference<Set<TupleVO>>() {
            });
            HashMap<String, Double> hashMap = new HashMap<>();
            for (TupleVO tuple : tuples) {
                hashMap.put(tuple.getElement(), tuple.getScore());
            }
            redisClient.zadd(key, hashMap);
            count++;
        }
        return count;
    }

    private Long repairDataByZSet(ArrayList<String> arrayList, Long count, boolean bigKeyFlag) {
        for (String line : arrayList) {
            String[] arr = line.split("\\|\\|");
            String key = arr[0];
            String value = arr[1];
            TupleVO vo = JSONObject.parseObject(value, new TypeReference<TupleVO>() {
            });
            redisClient.zadd(key, vo.getScore(), vo.getElement());
            count++;
        }
        return count;
    }

}
