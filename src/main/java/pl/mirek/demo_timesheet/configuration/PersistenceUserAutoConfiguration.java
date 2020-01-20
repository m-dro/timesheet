package pl.mirek.demo_timesheet.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
		basePackages = "pl.mirek.demo_timesheet.repository.user",
		entityManagerFactoryRef = "userEntityManager",
		transactionManagerRef = "userTransactionManager")


public class PersistenceUserAutoConfiguration {

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "security.datasource")
	public DataSource userDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Autowired
    private Environment env;
     
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        LocalContainerEntityManagerFactoryBean entityManager
          = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(userDataSource());
        entityManager.setPackagesToScan(
          new String[] { "pl.mirek.demo_timesheet.model.**" });
 
        HibernateJpaVendorAdapter vendorAdapter
          = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
          env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
          env.getProperty("hibernate.dialect"));
        entityManager.setJpaPropertyMap(properties);
 
        return entityManager;
    }

 
    @Primary
    @Bean
    public PlatformTransactionManager userTransactionManager() {
  
        JpaTransactionManager transactionManager
          = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
          userEntityManager().getObject());
        return transactionManager;
    }
	
}
