package org.zombie.apocalipse.api;

import java.net.UnknownHostException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfiguration {

	@Autowired
	private MongoConfig mongoConfig;

	@Autowired
	private MongoDbFactory mongoDbFactory;

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

		return mongoTemplate;
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws UnknownHostException {
		ServerAddress serverAddress = new ServerAddress(mongoConfig.host, mongoConfig.port);

		MongoClient mongoClient = null;

		if (mongoConfig.username == null || mongoConfig.username.isEmpty()) {
			mongoClient = getMongoClient(serverAddress);
		} else {
			mongoClient = getMongoClientWithUser(serverAddress);
		}

		return new SimpleMongoDbFactory(mongoClient, mongoConfig.database);
	}

	private MongoClient getMongoClientWithUser(ServerAddress serverAddress) {
		return new MongoClient(serverAddress,
				Collections.singletonList(MongoCredential.createCredential(mongoConfig.username,
						mongoConfig.authenticationDatabase, mongoConfig.password.toCharArray())));
	}

	private MongoClient getMongoClient(ServerAddress serverAddress) {
		return new MongoClient(serverAddress);
	}

}
