package fr.aoufi.springmvcsecurity.configuration;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "fr.aoufi.springmvcsecurity.configuration" })
@PropertySource({ "classpath:config.properties" })
public class DataConfiguration {
	
	private Logger logger = LoggerFactory.getLogger(DataConfiguration.class);
	
	@Autowired
	private Environment environment; 
	
	@Bean
	public DataSource dataSource() {		
		logger.info("Chargement de la source de données.");
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "fr.aoufi.springmvcsecurity.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	private Properties hibernateProperties() {
		
		Properties properties = new Properties();
		
		// Hibernate properties
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect", String.class));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql", Boolean.class));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto", String.class));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql", Boolean.class));
		
		// C3P0 properties
		properties.put("hibernate.c3p0.min_size", environment.getRequiredProperty("hibernate.c3p0.min_size", Integer.class));
		properties.put("hibernate.c3p0.max_size", environment.getRequiredProperty("hibernate.c3p0.max_size", Integer.class));
		properties.put("hibernate.c3p0.acquire_increment", environment.getRequiredProperty("hibernate.c3p0.acquire_increment", Integer.class));
		properties.put("hibernate.c3p0.timeout", environment.getRequiredProperty("hibernate.c3p0.timeout", Integer.class));
		properties.put("hibernate.c3p0.max_statements",	environment.getRequiredProperty("hibernate.c3p0.max_statements", Integer.class));
		return properties;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
}