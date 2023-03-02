package org.zombie.apocalipse.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"org.zombie.apocalipse.api"})
@EnableMongoRepositories
@EnableConfigurationProperties
@EnableAutoConfiguration
public class ZombieApocalipseApiApplication extends SpringBootServletInitializer {
	
	/**
	 * Executor do spring boot em modo standalone
	 * @param args Argumentos em linha de comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(ZombieApocalipseApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ApiConfig getApiConfig() {
		return new ApiConfig();
	}
	
}
