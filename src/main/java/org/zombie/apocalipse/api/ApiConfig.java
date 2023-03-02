package org.zombie.apocalipse.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig {

	/**
	 * default path of the api
	 */
	@Value("${api.path}")
	public String apiPath;

	/**
	 * Prefix to be used on all services
	 */
	@Value("${api.services-prefix}")
	public String appServicesPrefix;

}
