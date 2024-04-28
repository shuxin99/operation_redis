package shuxin.config.business;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "business.config")
public class BusinessConfig {

    private Integer scanCount;

    private Integer scanNumberLimit;

    private Long sleepMillis;

    private Integer hScanCount;

    private Integer hScanNumberLimit;

    private Long hSleepMillis;

    private Integer sScanCount;

    private Integer sScanNumberLimit;

    private Long sSleepMillis;

    private Integer zScanCount;

    private Integer zScanNumberLimit;

    private Long zSleepMillis;

    private Integer repairCountEveryTime;

}
