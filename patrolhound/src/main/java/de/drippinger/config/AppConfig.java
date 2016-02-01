package de.drippinger.config;

import de.drippinger.exception.ExceptionTranslator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;

/**
 * AppConfig
 *
 * @author Dennis Rippinger
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "de.drippinger")
@PropertySource("classpath:application.properties")
public class AppConfig {

	@Inject
	private Environment env;

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(destroyMethod = "close")
	public BasicDataSource getBasicDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(env.getProperty("spring.datasource.url"));
		basicDataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		basicDataSource.setUsername(env.getProperty("spring.datasource.username"));
		basicDataSource.setPassword(env.getProperty("spring.datasource.password"));

		return basicDataSource;
	}

	@Bean
	public DataSourceTransactionManager getTransactionManager(BasicDataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public TransactionAwareDataSourceProxy getTransactionAwareDataSourceProxy(BasicDataSource dataSource) {
		return new TransactionAwareDataSourceProxy(dataSource);
	}

	@Bean
	public ExceptionTranslator getJooqExceptionTranslator() {
		return new ExceptionTranslator();
	}

	@Bean
	public DataSourceConnectionProvider getDataSourceConnectionProvider(TransactionAwareDataSourceProxy proxy) {
		return new DataSourceConnectionProvider(proxy);
	}

	@Bean
	public DefaultConfiguration getJooqDefaultConfiguration(DataSourceConnectionProvider provider, ExceptionTranslator exceptionListener) {
		DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
		defaultConfiguration.setSQLDialect(SQLDialect.POSTGRES_9_4);
		defaultConfiguration.setConnectionProvider(provider);
		defaultConfiguration.setExecuteListenerProvider(new DefaultExecuteListenerProvider(exceptionListener));

		return defaultConfiguration;
	}

	@Bean
	public DSLContext getJooqContext(DefaultConfiguration configuration) {
		return new DefaultDSLContext(configuration);
	}


}
