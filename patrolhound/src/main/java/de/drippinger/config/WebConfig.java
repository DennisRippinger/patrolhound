package de.drippinger.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * WebConfig
 *
 * @author Dennis Rippinger
 */
@Configuration
@EnableAutoConfiguration
public class WebConfig {

//	@Bean
//	public FacesServlet facesServlet() {
//		return new FacesServlet();
//	}
//
//	@Bean
//	public ServletRegistrationBean facesServletRegistration() {
//		ServletRegistrationBean registration = new ServletRegistrationBean(
//				facesServlet(), "*.xhtml");
//		registration.setName("FacesServlet");
//		return registration;
//	}
//
//	@Bean
//	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
//		return new ServletListenerRegistrationBean<ConfigureListener>(
//				new ConfigureListener());
//	}

}