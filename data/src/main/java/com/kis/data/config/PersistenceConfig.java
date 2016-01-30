package com.kis.data.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by wanglong on 16-1-15.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableCaching
public class PersistenceConfig {
    @Autowired
    private Environment env;

    @Bean(name = "cacheManager")
    public EhCacheCacheManager ehCacheCacheManager(){
        return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
    }
    @Bean
    public LocalSessionFactoryBean getLocalSessionFactoryBean(){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(getDataSource());
        localSessionFactoryBean.setHibernateProperties(getHibernateProperties());
        localSessionFactoryBean.setPackagesToScan(env.getProperty("hibernate.package.to.scan"));
        return localSessionFactoryBean;
    }
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("/ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager(){
        return new HibernateTransactionManager(getLocalSessionFactoryBean().getObject());
    }
    @Bean
    public JpaVendorAdapter getJpaVendorAdapter(){
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }
    @Bean
    public DataSource getDataSource(){
        DruidDataSource druidDataSource =new DruidDataSource();
        druidDataSource.setUrl(env.getProperty("jdbc.url"));
        druidDataSource.setUsername(env.getProperty("jdbc.username"));
        druidDataSource.setPassword(env.getProperty("jdbc.password"));
        druidDataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        //初始化连接大小
        druidDataSource.setInitialSize(Integer.parseInt(env.getProperty("jdbc.initialSize")));
        //连接池最大使用连接数量
        druidDataSource.setMaxActive(Integer.parseInt(env.getProperty("jdbc.maxActive")));
        //连接池最小空闲
        druidDataSource.setMinIdle(Integer.parseInt(env.getProperty("jdbc.minIdle")));
        //获取连接最大等待时间
        druidDataSource.setMaxWait(Long.parseLong(env.getProperty("jdbc.maxWait")));
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty("jdbc.timeBetweenEvictionRunsMillis")));
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("jdbc.minEvictableIdleTimeMillis")));
        //打开removeAbandoned功能
        druidDataSource.setRemoveAbandoned(Boolean.parseBoolean(env.getProperty("jdbc.removeAbandoned")));
        druidDataSource.setRemoveAbandonedTimeout(Integer.parseInt(env.getProperty("jdbc.removeAbandonedTimeout")));
        druidDataSource.setLogAbandoned(Boolean.parseBoolean(env.getProperty("jdbc.logAbandoned")));
        return druidDataSource;
    }
    public Properties getHibernateProperties(){
        Properties hibernatProperties = new Properties();
        hibernatProperties.setProperty("hibernate.hbm2ddl.auto",env.getProperty("hibernate.hbm2ddl.auto"));
        hibernatProperties.setProperty("hibernate.dialect",env.getProperty("hibernate.dialect"));
        hibernatProperties.setProperty("hibernate.connection.autocommit",env.getProperty("hibernate.connection.autocommit"));
        hibernatProperties.setProperty("hibernate.current_session_context_class",env.getProperty("hibernate.current_session_context_class"));
        hibernatProperties.setProperty("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
        hibernatProperties.setProperty("hibernate.format_sql",env.getProperty("hibernate.format_sql"));
        hibernatProperties.setProperty("hibernate.cache.use_query_cache",env.getProperty("hibernate.cache.use_query_cache"));
        hibernatProperties.setProperty("hibernate.cache.use_second_level_cache",env.getProperty("hibernate.cache.use_second_level_cache"));
        hibernatProperties.setProperty("hibernate.cache.region.factory_class",env.getProperty("hibernate.cache.region.factory_class"));
        return hibernatProperties;
    }
}
