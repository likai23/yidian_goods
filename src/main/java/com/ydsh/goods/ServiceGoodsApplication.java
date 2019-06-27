package com.ydsh.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
 
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
@EnableDiscoveryClient
@EnableApolloConfig
public class ServiceGoodsApplication {

    public static void main(String[] args){
        Config config = ConfigService.getAppConfig();
        final String LOG_PATH="logging.path";
        final String PROFILES_ACTIVE="spring.profiles.active";
        final String LOG_NAME="logging.name";
        final String APPLICATION_NAME="spring.application.name";
        System.setProperty(PROFILES_ACTIVE,config.getProperty(PROFILES_ACTIVE, "prod"));
        System.setProperty(LOG_PATH,config.getProperty(LOG_PATH, "/logs"));
        System.setProperty(LOG_NAME,config.getProperty(APPLICATION_NAME, "UNDEFINED"));
        SpringApplication.run(ServiceGoodsApplication.class, args);
    }
}
