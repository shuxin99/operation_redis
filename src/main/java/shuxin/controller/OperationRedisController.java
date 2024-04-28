package shuxin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shuxin.service.IOperationRedisService;


@Slf4j
@RestController
@RequestMapping("/clean")
public class OperationRedisController {


    @Autowired
    private IOperationRedisService iOperationRedisService;

    @GetMapping("/scanByType")
    public String scanData4Redis(@RequestParam("match") String match, @RequestParam("writeFileFlag") String writeFileFlag, @RequestParam("type") String type, @RequestParam("deleteFlag") String deleteFlag, Integer jedisNumber) {
        String result = iOperationRedisService.scanData4Redis(match, writeFileFlag, type, deleteFlag, jedisNumber, false);
        log.info("scanData4Redis-执行结果,result:{}", result);
        return result;
    }


    @GetMapping("/repairData")
    public String repairData2Redis(@RequestParam("fileUrl") String fileUrl, @RequestParam("type") String type) {
        String result = iOperationRedisService.repairData2Redis(fileUrl, type, false);
        log.info("repairData-执行结果,result:{}", result);
        return result;
    }


    @GetMapping("/bigKeyScanDataByType")
    public String bigKeyScanDataByType(@RequestParam("match") String match, @RequestParam("writeFileFlag") String writeFileFlag, @RequestParam("type") String type, @RequestParam("deleteFlag") String deleteFlag, Integer jedisNumber) {
        String result = iOperationRedisService.scanData4Redis(match, writeFileFlag, type, deleteFlag, jedisNumber, true);
        log.info("bigKeyScanDataByType-执行结果,result:{}", result);
        return result;
    }


    @GetMapping("/repairData4BigKey")
    public String repairData4BigKey(@RequestParam("fileUrl") String fileUrl, @RequestParam("type") String type) {
        String result = iOperationRedisService.repairData2Redis(fileUrl, type, true);
        log.info("repairData4BigKey-修复行数,result:{}", result);
        return result;
    }


    @PostMapping("/changeSwitchInfo")
    public String changeSwitchInfo(@RequestParam("cleanSwitch") String cleanSwitch, @RequestParam("repairSwitch") String repairSwitch) {
        String result = iOperationRedisService.changeSwitchInfo(cleanSwitch, repairSwitch);
        log.info("更改开关标识,result:{}", result);
        return result;
    }


}
