package shuxin.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IOperationRedisService {
    /**
     * 处理数据
     *
     * @param writeFileFlag 是否写文件标识
     * @param count         处理数据行数
     * @param writer        输出流
     * @param list          本次scan出的key集合
     * @param type          redis数据类型
     * @param deleteFlag    是否删除redis数据
     * @param bigKeyFlag    是否是大key标识
     * @return 处理行数
     */
    Long handleDataByMultiType(String writeFileFlag, Long count, BufferedWriter writer, List<String> list, String type, String deleteFlag, boolean bigKeyFlag) throws IOException;

    /**
     * 修复数据
     *
     * @param type       redis数据类型
     * @param arrayList  读取的行数据
     * @param count      处理的行数
     * @param bigKeyFlag 是否是大key标识
     * @return 处理的行数
     */
    Long repairDataByType(String type, ArrayList<String> arrayList, Long count, boolean bigKeyFlag);

    String scanData4Redis(String match, String writeFileFlag, String type, String deleteFlag, Integer jedisNumber, boolean bigKeyFlag);

    String repairData2Redis(String fileUrl, String type, boolean bigKeyFlag);

    String changeSwitchInfo(String cleanSwitch, String repairSwitch);
}
