package com.korotkov.mainCurrentApp.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.korotkov")
@EnableTransactionManagement
@PropertySource(value = "classpath:db.properties")
public class HibernateConfig {

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    //configs for main application
    private Properties hibernateMainProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("dbMain.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("dbMain.hibernate.show_sql"));
        return properties;
    }

    @Primary
    @Bean("dataSourceMain")
    public DataSource dataSourceMain() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("dbMain.jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("dbMain.jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("dbMain.jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("dbMain.jdbc.password"));
        return dataSource;
    }

    @Bean("sessionFactoryMain")
    public LocalSessionFactoryBean sessionFactoryMain() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceMain());
        sessionFactory.setPackagesToScan("com.korotkov.mainCurrentApp");
        sessionFactory.setHibernateProperties(hibernateMainProperties());
        return sessionFactory;
    }

    @Bean("transactionManagerMain")
    public HibernateTransactionManager transactionManagerMain() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryMain().getObject());
        return transactionManager;
    }

    //configs for credit conveyor (CRM) application
    private Properties hibernateCRMProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("dbCrm.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("dbCrm.hibernate.show_sql"));
        properties.put("hibernate.default_schema", environment.getRequiredProperty("dbCrm.jdbc.defaultSchema"));
        return properties;
    }

    @Bean("dataSourceCRM")
    public DataSource dataSourceCRM() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("dbCrm.jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("dbCrm.jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("dbCrm.jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("dbCrm.jdbc.password"));
        dataSource.setMaxIdle(2);
        dataSource.setMinEvictableIdleTimeMillis(3000L);
        return dataSource;
    }

    @Bean("sessionFactoryCRM")
    public LocalSessionFactoryBean sessionFactoryCRM() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceCRM());
        sessionFactory.setPackagesToScan("com.korotkov.creditCRM");
        sessionFactory.setHibernateProperties(hibernateCRMProperties());
        return sessionFactory;
    }

    @Bean("transactionManagerCRM")
    public HibernateTransactionManager transactionManagerCRM() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryCRM().getObject());
        return transactionManager;
    }



    //configs for Vicidial application
    private Properties hibernateVicidialProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("dbVicidial.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("dbVicidial.hibernate.show_sql"));
        return properties;
    }

    @Bean("dataSourceVicidial")
    public DataSource dataSourceVicidial() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("dbVicidial.jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("dbVicidial.jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("dbVicidial.jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("dbVicidial.jdbc.password"));
        dataSource.setMaxIdle(2);
        dataSource.setMinEvictableIdleTimeMillis(3000L);
        return dataSource;
    }

    @Bean("sessionFactoryVicidial")
    public LocalSessionFactoryBean sessionFactoryVicidial() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceVicidial());
        sessionFactory.setPackagesToScan("com.korotkov.vicidial");
        sessionFactory.setHibernateProperties(hibernateVicidialProperties());
        return sessionFactory;
    }

    @Bean("transactionManagerVicidial")
    public HibernateTransactionManager transactionManagerVicidial() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryVicidial().getObject());
        return transactionManager;
    }



}
