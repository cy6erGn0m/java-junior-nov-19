package ru.levelp.junior.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = "ru.levelp.junior")
@EnableTransactionManagement(proxyTargetClass = true)
public class AppConfig {
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(factory);
        return transactionManager;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean emf() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPersistenceUnitName("TestPersistenceUnit");
        return bean;
    }
}
