package com.zagvladimir.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Log4j2
@Configuration
public class PersistenceProvidersConfiguration {

//    @Bean(name = "sessionFactory")
//    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception{
//
//        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
//        factoryBean.setPackagesToScan("com.zagvladimir");
//        factoryBean.setDataSource(dataSource);
//        factoryBean.setHibernateProperties(getAdditionalProperties());
//        factoryBean.afterPropertiesSet();
//
//        SessionFactory sf = factoryBean.getObject();
//        log.info("## getSessionFactory: " + sf);
//        return sf;
//    }

    //Entity Manager
    @Autowired
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.zagvladimir");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(getAdditionalProperties());

        return em;
    }

    private Properties getAdditionalProperties(){
        Properties properties = new Properties();

        properties.put("hibernate.show_sql", "true");
//        properties.put("hibernate.default_schema", "public");
        properties.put("current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        return properties;

    }
}
