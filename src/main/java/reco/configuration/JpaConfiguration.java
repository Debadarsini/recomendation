package reco.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages="reco.repositories",
entityManagerFactoryRef="entityManagerFactory",transactionManagerRef="transactionManager")
@EnableTransactionManagement
public class JpaConfiguration {
    
    @Autowired
    private Environment environment;
    
    @Value("${datasource.reco.maxPoolSize:10}")
    private int maxPoolSize;
    
    @Bean
    @Primary
    @ConfigurationProperties(prefix="datasource.reco")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
        
    }
    
    @Bean
    public DataSource dataSource(){
        DataSourceProperties dsprop = dataSourceProperties();
        HikariDataSource dataSource = (HikariDataSource)DataSourceBuilder
                            .create(dsprop.getClassLoader())
                            .driverClassName(dsprop.getDriverClassName())
                            .url(dsprop.getUrl())
                            .username(dsprop.getDataUsername())
                            .password(dsprop.getDataPassword())
                            .type(HikariDataSource.class)
                            .build();
        
        dataSource.setMaximumPoolSize(maxPoolSize);
        return dataSource;
        
    }
    
    @Bean 
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("reco.model");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
        
    }
    
    private Properties jpaProperties() {

        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.reco.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("datasource.reco.hibernate.hbm2ddl.method"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.reco.hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.reco.hibernate.format_sql"));
        if(null != environment.getRequiredProperty("datasource.reco.defaultSchema") || 
        !"".equals(environment.getRequiredProperty("datasource.reco.defaultSchema"))){
            properties.put("hibernate.default_schema", environment.getRequiredProperty("datasource.reco.defaultSchema"));
        }
        return properties;
    }

    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter provider = new HibernateJpaVendorAdapter();
        
        return provider;
    }
    
    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
   
}
