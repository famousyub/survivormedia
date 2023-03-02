package org.zombie.apocalipse.api;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ApiCoreITCase extends ZombieApocalipseApiApplicationTests {
	
	@Autowired
	private ApplicationContext context;

	@Test
	public void shouldLoadContext() {
		Assert.assertNotNull(context);
	}

}
