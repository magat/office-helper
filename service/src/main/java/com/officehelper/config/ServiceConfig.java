package com.officehelper.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "com.officehelper.service")
@EnableTransactionManagement
public class ServiceConfig {
    /*
    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    */

    @Bean(name = "transactionManager") //Enables @Transactional
    public HibernateTransactionManager txName(DataSource dataSource, SessionFactory sessionFactory) throws IOException {
        HibernateTransactionManager htManager = new HibernateTransactionManager();
        htManager.setSessionFactory(sessionFactory);
        htManager.setDataSource(dataSource);
        return htManager;
    }
}
