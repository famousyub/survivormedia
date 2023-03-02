package org.zombie.apocalipse.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * class that map mongodb configuration
 */
@Component
public class MongoConfig {

	@Value("${spring.data.mongodb.host}")
	public String host;

	@Value("${spring.data.mongodb.database}")
	public String database;

	@Value("${spring.data.mongodb.authentication-database}")
	public String authenticationDatabase;

	@Value("${spring.data.mongodb.username}")
	public String username;

	@Value("${spring.data.mongodb.password}")
	public String password;

	@Value("${spring.data.mongodb.port}")
	public int port;
	
}
