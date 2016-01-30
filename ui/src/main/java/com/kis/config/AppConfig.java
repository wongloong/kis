package com.kis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanglong on 16-1-15.
 */
@Configuration
@ComponentScan(basePackages = "com.",excludeFilters = @ComponentScan.Filter({Controller.class, RestController.class,Configuration.class}))
@EnableScheduling
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class AppConfig {

}
