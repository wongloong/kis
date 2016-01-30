package com.kis.data.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanglong on 16-1-15.
 */
@Configuration
@ComponentScan(basePackages = "com.kis",excludeFilters = @ComponentScan.Filter({Controller.class, RestController.class,Configuration.class}))
@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching
@EnableTransactionManagement
@Import(PersistenceConfig.class)
public class AppConfig {

}
