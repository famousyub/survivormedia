package org.zombie.apocalipse.api;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ZombieApocalipseApiApplicationTests {
	
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");
	
	protected ObjectMapper jsonMapper = new ObjectMapper();
	
	@Inject
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = webAppContextSetup(this.wac).apply(
				documentationConfiguration(this.restDocumentation).uris().withHost("survivalnetwork.org").withPort(80))
				.build();
	}
	
	public ResultActions perform(MockHttpServletRequestBuilder requestBuilder) throws Exception {
		return this.mockMvc.perform(requestBuilder);
	}
	
	@Test
	public void shouldLoadContext() {
		Assert.assertNotNull(wac);
	}
}
