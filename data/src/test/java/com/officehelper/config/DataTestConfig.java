package com.officehelper.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@Import(DataConfig.class)
public class DataTestConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/**/db.test.properties"));
        return propertyConfigurer;
    }

    @Bean //Necessary for Spring to parse ${} of properties
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(@Value("${db.username}") String dbusername,
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

    @Bean(name = "transactionManager") //Enables @Transactional
    public HibernateTransactionManager txName(DataSource dataSource, SessionFactory sessionFactory) throws IOException {
        HibernateTransactionManager htManager = new HibernateTransactionManager();
        htManager.setSessionFactory(sessionFactory);
        htManager.setDataSource(dataSource);
        return htManager;
    }
}