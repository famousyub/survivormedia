package org.zombie.apocalipse.api.reports;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.zombie.apocalipse.api.ZombieApocalipseApiApplicationTests;

public class SurvivorReportsControllerITCase extends ZombieApocalipseApiApplicationTests {
	
	private static final String BASE_URL = "/v1/app/reports/";
	
	@Test
	public void shouldReturnPercentageOfInfectedSurvivors() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get(BASE_URL + "infected");

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.percentage").value(9.0))
				.andDo(document("reports/perc-infected",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}
	
	@Test
	public void shouldReturnPercentageOfNonInfectedSurvivors() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get(BASE_URL + "not_infected");

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.percentage").value(91.0))
				.andDo(document("reports/perc-non-infected",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}
	
	@Test
	public void shouldReturnAverageAmounmtOfResourcesPerSurvivor() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get(BASE_URL + "inventory");

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.waterPerSurvivor").value(5.0))
				.andExpect(jsonPath("$.data.ammunitionPerSurvivor").value(4.0989010989010985))
				.andExpect(jsonPath("$.data.foodPerSurvivor").value(4.516483516483516))
				.andExpect(jsonPath("$.data.medicationPerSurvivor").value(4.824175824175824))
				.andDo(document("reports/avg-amount-resources",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}
	
	@Test
	public void shouldReturnPointsLostDueToInfectedSurvivors() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get(BASE_URL + "points_lost");

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.pointsLost").value(361))
				.andDo(document("reports/points-lost",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}
	
}
