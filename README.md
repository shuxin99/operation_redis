# operation_redis

#### 一、应用简介---主要针对于常用的五大Redis数据结构(String,Hash,Set,List,Zset)

- 应用基于Redis的scan命令扫描key，主要是解决线上存储有误，或者不在需要且没有设置过期时间的key进行清理，或者Redis进行数据迁移，或是以文件的格式进行数据备份以及对Redis中key数据量的统计。这部分key大都伴随着数据量庞大，人为处理起来比较麻烦，此应用由此诞生。

- 应用支持Redis以多种方式部署，现已支持单点、哨兵、集群方式的部署
- 应用支持指定key以及模式匹配(正则表达式)
- 应用支持key数据量小的普通key，也支持单key数据量大的大key(未知数据量key也支持处理)

#### 二、功能介绍

##### 接口说明

匹配及处理数据接口

```java
public String scanData4Redis(@RequestParam("match") String match,
                             @RequestParam("writeFileFlag") String writeFileFlag, 
                             @RequestParam("type") String type, 
                             @RequestParam("deleteFlag") String deleteFlag, 
                             Integer jedisNumber);

match: 需要匹配的key，支持正则
writeFileFlag: 扫描出的key以及存储的数据是否写入文件
type: 需要处理的数据类型
deleteFlag: 是否需要删除匹配的数据
jedisNumber：集群模式下开始匹配节点的序号，用于跳过一些节点的处理
```

修复数据接口

```java
public String repairData2Redis(@RequestParam("fileUrl") String fileUrl,
                               @RequestParam("type") String type)
    
fileUrl: 匹配及处理接口写文件的路径
type: 需要修复数据的数据类型
```

这些是对普通key的处理，及需要处理的key的数据量不大

还有两个接口是对这两个接口的功能扩展，是为了处理key下数据量比较大的情况

key为hash类型的，继续使用hscan取field，分批处理

key为set类型的，继续使用sscan取member，分批处理

key为zset类型的，继续使用zscan取member，分批进行处理

key为list类型的，使用lrange(key，start,end) 控制下标方式分批进行处理

1. ##### 数据清理

   writeFileFlag建议设为true, deleteFlag为true, writeFileFlag为true时，匹配的key使用scan扫描到，会把key数据按照格式写入文件，deleteFlag为true时，写入文件后同时也会把该key删除掉

2. ##### 数据备份

   writeFileFlag设为true, deleteFlag为false, writeFileFlag为true时，匹配的key使用scan扫描到，会把key数据按照格式写入文件，deleteFlag为false时，不会删除数据

3. ##### 数据迁移

   writeFileFlag设为true，deleteFlag为false，匹配的key扫描完并写入文件后。如果迁移到别的Redis,修改配置文件调用修复数据接口，就能将文件中的数据写入到Redis中。

   或者自定义的迁移方案，比如迁移到mysql库中，对生成的文件做处理即可，这块需要自行实现相关逻辑。

4. ##### 数据统计

   writeFileFlag设为false，deleteFlag为false，接口处理完后会返回处理该key类型的数量

5. ##### 数据恢复

   传入处理接口后生成的文件路径，和文件存储的数据类型，即可将文件中的数据写入Redis

6. ##### 跳出执行流程

   执行数据处理和修复数据时，如果部署服务器的状态不太好，可以通过开关的形式将执行流程关闭。再执行的过程中读取Redis中开关的值，如果开关为打开，执行流程将立即退出。

   退出执行流程后再次执行时，jedisNumber这个参数就派上用场了，日志中会打印目前执行的节点数，可用于跳过已经执行过的节点，从跳出的那个节点重新执行，重新执行时

##### 		控制流程跳出开关接口

```java
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
```

除此之外，每次scan的count值，以及为了保证服务器的状态所作的处理需要的值都是可配置的。再配置文件中支持可以配，想要实施生效的话可以引入nacos并且配置动态刷新即可。



#### 三、**Shell脚本方式请求接口批量处理(可选) + 部署**

1.新建operation_redis.sh脚本文件，放到部署机器的特定目录下，新建/operation_redis文件夹，核实无误后请新建脚本并赋权。

operation_redis_list.txt文件也刚在脚本的该目录下，里面写的是需要清理的Redis的key前缀及Redis数据类型。

例如：

```shell
#key,数据类型
test:key*,hash
test:setKey*,set
```

执行脚本时还需要传入三个参数，第一个参数为是否写入文件标识（true or false），第二个参数为是否删除标识 （true or false），第三个是从第几个节点开始执行(从1开始)

```shell
#!/bin/bash
cat operation_redis_list.txt | while read line
do
    echo $line
    lineContent=$line
    array=(${lineContent//,/ })
    key="${array[0]}"
    typeCode="${array[1]}"
    echo $key
    echo $typeCode  
    curl  -v -H "Content-Type:application/json;charset=UTF-8" -X POST "http://localhost:10086/operation_redis/clean/bigKeyScanDataByType?match=$key&writeFileFlag=$1&type=$typeCode&deleteFlag=$2&jedisNumber=$3"
done
echo "$(date +%Y%m%d_%T) 完成" >> log.txt
```

2.上传应用jar包文件到部署机器的特定目录下的/operation_redis 文件夹，若以存在删掉重新上传即可。

3.新建启动应用脚本：operation_redis/start.sh 环境激活参数为（dev, test, pre, pro）,分别对应本地环境，测试环境，预发布环境，生产环境

```shell
#!/bin/bash
DATE="$(date +%Y%m%d)"
JAVA_HOME="/xxx/xxx/jdk1.8.0_73"
JAR="/xxx/operation_redis/operation_redis.jar"
LOGDIR="/xxx/logs"
LOGFILE="${LOGDIR}/operation_redis-${DATE}.log"

nohup ${JAVA_HOME}/bin/java \
-Xms256m -Xmx256m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=64m \
-jar ${JAR} \
--file.encoding=utf-8 \
--spring.profiles.active=xxx \
--server.port=xxx \
>> ${LOGFILE} 2>&1 &
```

4.日志及生成的文件都会放在operation_redis该目录下。

