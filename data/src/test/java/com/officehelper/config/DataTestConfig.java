package com.officehelper.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@Import(DataConfig.class)
@PropertySource("classpath:/db.test.properties")
public class DataTestConfig {

    @Value("${db.test.username}")
    private String dbusername;

    @Value("${db.test.pswd}")
    private String dbpswd;

    @Value("${db.test.driver}")
    private String dbdriver;

    @Value("${db.test.url}")
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

    @Bean(name = "transactionManager") //Enables @Transactional
    public HibernateTransactionManager txName(DataSource dataSource, SessionFactory sessionFactory) throws IOException {
        HibernateTransactionManager htManager = new HibernateTransactionManager();
        htManager.setSessionFactory(sessionFactory);
        htManager.setDataSource(dataSource);
        return htManager;
    }
}