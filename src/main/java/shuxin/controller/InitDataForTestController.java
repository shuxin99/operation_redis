package shuxin.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shuxin.config.RedisClient;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/clean")
public class InitDataForTestController {

    @Resource
    private RedisClient jedisCluster;

    @GetMapping("/initData4Hash")
    public String initData4Redis() {
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            HashMap<String, String> tempMap = new HashMap<>();
            for (int j = 0; j < 20; j++) {
                tempMap.put("j:" + j, String.valueOf(j));
            }
            jedisCluster.hmset("20231120_hashTest" + i, tempMap);
            count++;
        }
        if (count == 1000) {
            return "OK";
        }
        return "FAIL";
    }

    @GetMapping("/initData4String")
    public String initData4String() {
        int count = 0;
        for (int i = 1; i <= 5; i++) {
            jedisCluster.set("stringTest" + i, String.valueOf(i));
            count++;
        }
        if (count == 5) {
            return "OK";
        }
        return "FAIL";
    }

    @GetMapping("/initData4Set")
    public String initData4Set() {
        int count = 0;
        for (int i = 1; i <= 10000; i++) {
            for (int j = 1; j <= 10; j++) {
                jedisCluster.sadd("setTest" + i, String.valueOf(j));
            }
            count++;
        }
        if (count == 10000) {
            return "OK";
        }
        return "FAIL";
    }

    @GetMapping("/initData4List")
    public String initData4List() {
        int count = 0;
        for (int i = 1; i <= 10000; i++) {
            for (int j = 1; j <= 10; j++) {
                jedisCluster.rpush("listTest" + i, String.valueOf(j));
            }
            count++;
        }
        if (count == 10000) {
            return "OK";
        }
        return "FAIL";
    }

    @GetMapping("/initData4ZSet")
    public String initData4ZSet() {
        int count = 0;
        for (int i = 1; i <= 10000; i++) {
            count++;
            HashMap<String, Double> hashMap = new HashMap<>();
            for (int j = 1; j < 10; j++) {
                int num = j + 100;
                hashMap.put(String.valueOf(j), Double.valueOf(String.valueOf(num)));
            }
            jedisCluster.zadd("ZSetTest" + i, hashMap);
        }
        if (count == 10000) {
            return "OK";
        }
        return "FAIL";
    }


    @GetMapping("/initData4Line")
    public String initData4Line() {
        int count = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= 10000; i++) {
            count++;
            builder.append(i);
        }
        try (FileWriter fileWriter = new FileWriter(new File("redisData-line.txt"), true)) {
            fileWriter.write(builder.toString());
            fileWriter.flush();
        } catch (IOException e) {
            log.error("initData4Line出异常啦", e);
        }
        if (count == 10000) {
            return "OK";
        }
        return "FAIL";
    }


    @GetMapping("/initData4LineByHash")
    public String initData4LineByHash() {
        int count = 0;

        try (FileWriter fileWriter = new FileWriter(new File("redisData-lineByHash.txt"), true)) {
            for (int i = 1; i <= 1000; i++) {
                count++;
                HashMap<String, String> hashMap = new HashMap<>();
                for (int j = 1; j <= 3000; j++) {
                    hashMap.put(String.valueOf(j), UUID.randomUUID().toString());
                }
                fileWriter.write(JSON.toJSONString(hashMap) + "\r\n");
                fileWriter.flush();
            }

        } catch (IOException e) {
            log.error("initData4Line出异常啦", e);
        }
        if (count == 1000) {
            return "OK";
        }
        return "FAIL";
    }


    @GetMapping("/initData4BigKeyHash")
    public String initData4BigKeyHash() {
        int count = 0;
        for (int i = 1; i <= 100; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            for (int j = 1; j <= 100000; j++) {
                hashMap.put("field" + j, "value" + j);
                count++;
            }
            jedisCluster.hmset("hashBigKey" + i, hashMap);
        }
        log.info("总共{}个field", count);
        return "OK";
    }

    @GetMapping("/initData4BigKeyList")
    public String initData4BigKeyList() {
        for (int i = 1; i <= 20; i++) {
            String key = "List4BigKey" + i;
            for (int j = 1; j <= 10000; j++) {
                jedisCluster.lpush(key, "List4BigKey:Value:" + j);
            }
            log.info("key:{}" + "总共{}个元素", key, jedisCluster.llen(key));
        }
        return "OK";
    }

    @GetMapping("/initData4BigKeySet")
    public String initData4BigKeySet() {
        for (int i = 1; i <= 20; i++) {
            String key = "Set4BigKey" + i;
            for (int j = 1; j <= 10000; j++) {
                jedisCluster.sadd(key, "Set4BigKey:Value:" + j);
            }
            log.info("key:{}" + "总共{}个元素", key, jedisCluster.scard(key));
        }
        return "OK";
    }

    @GetMapping("/initData4BigKeyZSet")
    public String initData4BigKeyZSet() {
        for (int i = 1; i <= 20; i++) {
            String key = "ZSet4BigKey" + i;
            for (int j = 1; j <= 10000; j++) {
                jedisCluster.zadd(key, (double) j, "ZSet4BigKey:Value:" + j);
            }
            log.info("key:{}" + "总共{}个元素", key, jedisCluster.zcard(key));
        }
        return "OK";
    }

    @GetMapping("/readData4Line")
    public String readData4Line() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("redisData-line.txt")));
        String line;
        int count = 0;
        ArrayList<String> arrayList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            arrayList.add(line);
        }
        for (String lineStr : arrayList) {
            count = lineStr.length();
            log.info("该行的length：{}", lineStr.length());
        }
        if (count == 10000) {
            return "OK";
        }
        return "FAIL";
    }


    @GetMapping("/readData4LineByHash")
    public String readData4LineByHash() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("redisData-lineByHash.txt")));
        String line;
        int count = 0;
        ArrayList<String> arrayList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            arrayList.add(line);
            count++;
        }
        for (String lineStr : arrayList) {
            log.info("该行的length：{}", lineStr.length());
        }
        log.info("count:{}", count);
        if (count == 1000) {
            return "OK";
        }
        return "FAIL";
    }


}
