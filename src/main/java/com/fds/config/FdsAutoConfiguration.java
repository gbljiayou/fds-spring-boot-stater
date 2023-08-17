package com.fds.config;

import com.xiaomi.infra.galaxy.fds.client.FDSClientConfiguration;
import com.xiaomi.infra.galaxy.fds.client.GalaxyFDS;
import com.xiaomi.infra.galaxy.fds.client.GalaxyFDSClient;
import com.xiaomi.infra.galaxy.fds.client.credential.BasicFDSCredential;
import com.xiaomi.infra.galaxy.fds.client.credential.GalaxyFDSCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈功能概述〉<br>
 *
 * @orojectName: fds-spring-boot-stater
 * @author: gbl
 * @date: 2021/6/21 4:16 下午
 */
@Configuration
@EnableConfigurationProperties({FdsPropeties.class})
@ConditionalOnClass({GalaxyFDSClient.class, GalaxyFDSCredential.class, GalaxyFDS.class})
public class FdsAutoConfiguration {
    
    @Autowired
    private FdsPropeties fdsPropeties;
    
    @Bean
    @ConditionalOnMissingBean
    public GalaxyFDS fdsClient() {
        GalaxyFDSCredential credential = new BasicFDSCredential(fdsPropeties.getAccessId(),
                fdsPropeties.getSecretKey());
        FDSClientConfiguration fdsConfig = new FDSClientConfiguration(fdsPropeties.getIntranetEndpoint());
        fdsConfig.enableHttps(fdsPropeties.isEnableHttps());
        fdsConfig.enableCdnForDownload(fdsPropeties.isEnableCdnForUpload());
        // 构建 fdsClient 对象
        GalaxyFDS fdsClient = new GalaxyFDSClient(credential, fdsConfig);
        return fdsClient;
    }
}
