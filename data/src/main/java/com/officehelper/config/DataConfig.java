package com.officehelper.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.officehelper.dao")
@PropertySource("classpath:/db.properties")
public class DataConfig {

    @Value("${db.username}")
    private String dbusername;

    @Value("${db.pswd}")
    private String dbpswd;

    @Value("${db.driver}")
    private String dbdriver;

    @Value("${db.url}")
    private String dburl;

    @Bean //Necessary for Spring to parse ${} of properties
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
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

