package de.drippinger.config;

import de.drippinger.view.ViewScope;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ScopeConfig
 *
 * @author Dennis Rippinger
 */
@Configuration
public class ScopeConfig {

	@Bean
	public CustomScopeConfigurer getCustomScopeConfigurer() {
		CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
		customScopeConfigurer.addScope("view", new ViewScope());

		return customScopeConfigurer;
	}
}
