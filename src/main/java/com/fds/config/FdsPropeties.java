package com.fds.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 〈功能概述〉<br>
 *
 * @orojectName: fds-spring-boot-stater
 * @author: gbl
 * @date: 2021/6/21 4:16 下午
 */
@ConfigurationProperties("fds.config")
@Data
public class FdsPropeties {
    
    private String accessId;
    
    private String secretKey;
    
    private String bucketName;
    
    private String intranetEndpoint;
    
    private String externalEndpoint;
    
    private boolean enableHttps;
    
    private boolean enableCdnForUpload;
    
}
