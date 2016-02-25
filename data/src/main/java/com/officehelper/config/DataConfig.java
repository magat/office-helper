package com.officehelper.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "com.officehelper.dao")
public class DataConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/**/db.properties"));
        return propertyConfigurer;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(
            @Value("${db.username}") String dbusername,
            @Value("${db.pswd}") String dbpswd,
            @Value("${db.driver}") String dbdriver,
            @Value("${db.url}") String dburl) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(dbdriver);
        dataSource.setUrl(dburl);
        dataSource.setUsername(dbusername);
        dataSource.setPassword(dbpswd);
        return dataSource;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.scanPackages("com.officehelper.entity"); //We use Spring scanPackage instead of Hibernate's addAnnotatedClass
        return sessionBuilder.buildSessionFactory();
    }
}

