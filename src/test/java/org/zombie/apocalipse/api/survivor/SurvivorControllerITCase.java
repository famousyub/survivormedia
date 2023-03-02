package org.zombie.apocalipse.api.survivor;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.zombie.apocalipse.api.ZombieApocalipseApiApplicationTests;
import org.zombie.apocalipse.api.survivor.dto.SurvivorDTO;
import org.zombie.apocalipse.api.survivor.dto.SurvivorInventoryDTO;
import org.zombie.apocalipse.api.survivor.dto.TradeDTO;

public class SurvivorControllerITCase extends ZombieApocalipseApiApplicationTests {

	private static final String BASE_URL = "/v1/app/survivors/";
	
	@Test
	public void shouldReturnAllSurvivors() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get(BASE_URL);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("survivors/get-survivors",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}
	
	@Test
	public void shouldReturnSpecificSurvivor() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = get(BASE_URL + "58eaabaa2b43926980b5d650");

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.age").value(56))
				.andExpect(jsonPath("$.data.name").value("Wolverine Jackman"))
				.andExpect(jsonPath("$.data.gender").value("dont-ask"))
				.andExpect(jsonPath("$.data.inventory.water").value(6))
				.andExpect(jsonPath("$.data.inventory.food").value(8))
				.andExpect(jsonPath("$.data.inventory.medication").value(9))
				.andExpect(jsonPath("$.data.inventory.ammunition").value(2))
				.andDo(document("survivors/get-survivor",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}

	@Test
	public void shouldCreateNewSurvivor() throws Exception {
		SurvivorDTO dto = new SurvivorDTO();
		dto.setAge(24);
		dto.setGender("female");
		dto.setLastLocation(-23.550520, -46.633309);
		dto.setName("Jane Doe");
		SurvivorInventoryDTO inventory = new SurvivorInventoryDTO();
		inventory.setAmmunition(10);
		inventory.setFood(5);
		dto.setInventory(inventory);

		String json = jsonMapper.writeValueAsString(dto);
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL).content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.age").value(24))
				.andExpect(jsonPath("$.data.gender").value("female"))
				.andExpect(jsonPath("$.data.name").value("Jane Doe"))
				.andExpect(jsonPath("$.data.inventory.ammunition").value(10))
				.andExpect(jsonPath("$.data.inventory.food").value(5))
				.andDo(document("survivors/create-survivor",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}
	
	@Test
	public void shouldUpdateSurvivor() throws Exception {
		SurvivorDTO dto = new SurvivorDTO();
		dto.setLastLocation(-23.550324, -46.633908);
		
		String json = jsonMapper.writeValueAsString(dto);
		MockHttpServletRequestBuilder requestBuilder = patch(BASE_URL + "58eaabaa2b43926980b5d650").content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$..message").value("Survivor updated."))
				.andDo(document("survivors/update-survivor",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}

	@Test
	public void shouldFlagAsInfected() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d678/report")
				.param("infectedName", "Ms. De Rivia");

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("survivors/flag-infected-survivor",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}
	
	@Test
	public void shouldNotCreateNewSurvivorWithoutCorrectLastLocation() throws Exception {
		SurvivorDTO dto = new SurvivorDTO();
		dto.setAge(24);
		dto.setGender("female");
		dto.setLastLocation(-23.550520);
		dto.setName("Jane Doe");
		SurvivorInventoryDTO inventory = new SurvivorInventoryDTO();
		inventory.setAmmunition(10);
		inventory.setFood(5);
		dto.setInventory(inventory);

		String json = jsonMapper.writeValueAsString(dto);
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL).content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("Having a correct location is always necessary. Please send latitude and longitude."))
				.andDo(print());
	}
	
	@Test
	public void shouldNotFlagAsInfectedIfNameNotFound() throws Exception {
		String infectedName = "Thor Odinson";
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d632/report")
				.param("infectedName", infectedName);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("The given survivor name could not be found: " + infectedName))
				.andDo(print());
	}
	
	@Test
	public void shouldNotFlagAsInfectedIfHaveAlreadyReportedTheSameSurvivor() throws Exception {
		String infectedName = "Geralt Cat-Woman";
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d632/report")
				.param("infectedName", infectedName);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("You have already reported this survivor."))
				.andDo(print());
	}
	
	@Test
	public void shouldNotFlagAsInfectedWithReporterInvalidId() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "anyInvalidId/report")
				.param("infectedName", "Ms. De Rivia");

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("A valid id is required. Currently: " + "anyInvalidId"))
				.andDo(print());
	}
	
	@Test
	public void shouldNotFlagReporterAsInfected() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaaba92b43926980b5d5fe/report")
				.param("infectedName", "Ms. De Rivia");

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("Can't report and be reported as infected."))
				.andDo(print());
	}
	
	@Test
	public void shouldNotFlagAsAsInfectedIfReportedOrReporterWereNotFound() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58e9c8a42b439238f0d3b76d/report")
				.param("infectedName", "Ms. De Rivia");

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("The given IDs could not be found: 58e9c8a42b439238f0d3b76d"))
				.andDo(print());
	}
	
	@Test
	public void shouldNotUpdateSurvivorNotFound() throws Exception {
		SurvivorDTO dto = new SurvivorDTO();
		dto.setId(new ObjectId().toHexString());
		dto.setLastLocation(-23.550324, -46.633908);
		
		String json = jsonMapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder requestBuilder = patch(BASE_URL + dto.getId()).content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$..message").value("Not possible to update the survivor. No survivor was found for the given ID: " + dto.getId()))
				.andDo(print());
	}
	
	@Test
	public void shouldNotAllowTradeWithNothingToTrade() throws Exception {
		TradeDTO dto = new TradeDTO();
		dto.setBuyerId("58eaabaa2b43926980b5d650");
		dto.setBuyerTrade(new SurvivorInventoryDTO());
		dto.setSellerTrade(new SurvivorInventoryDTO());
		
		String json = jsonMapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d678/trade").content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("Don't be cheap, please inform something to trade."))
				.andDo(print());
	}
	
	@Test
	public void shouldNotAllowTradeOwnItems() throws Exception {
		TradeDTO dto = new TradeDTO();
		dto.setBuyerId("58eaabaa2b43926980b5d650");
		dto.setBuyerTrade(new SurvivorInventoryDTO());
		dto.setSellerTrade(new SurvivorInventoryDTO());
		
		String json = jsonMapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d650/trade").content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("Cannot perform trade between the same survivor."))
				.andDo(print());
	}
	
	@Test
	public void shouldNotPerformTradeWithAZombie() throws Exception {
		TradeDTO dto = new TradeDTO();
		dto.setBuyerId("58eaabaa2b43926980b5d650");
		dto.setBuyerTrade(new SurvivorInventoryDTO());
		dto.setSellerTrade(new SurvivorInventoryDTO());
		
		String json = jsonMapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d604/trade").content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("Careful now! do not trade with a zombie."))
				.andDo(print());
	}
	
	@Test
	public void shouldNotPerformTradeWithNotEnoughResources() throws Exception {
		TradeDTO dto = new TradeDTO();
		dto.setBuyerId("58eaabaa2b43926980b5d650");
		
		SurvivorInventoryDTO seller = new SurvivorInventoryDTO();
		seller.setAmmunition(5);
		dto.setSellerTrade(seller);
		
		SurvivorInventoryDTO buyer = new SurvivorInventoryDTO();
		buyer.setFood(2);
		dto.setBuyerTrade(buyer);
		
		String json = jsonMapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d678/trade").content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("Trade could not be completed. Trader do not have enough of the item: AMMUNITION"))
				.andDo(print());
	}
	
	@Test
	public void shouldNotPerformTradeWithDifferentItemValue() throws Exception {
		TradeDTO dto = new TradeDTO();
		dto.setBuyerId("58eaabaa2b43926980b5d650");
		
		SurvivorInventoryDTO seller = new SurvivorInventoryDTO();
		seller.setMedication(5);
		dto.setSellerTrade(seller);
		
		SurvivorInventoryDTO buyer = new SurvivorInventoryDTO();
		buyer.setFood(2);
		dto.setBuyerTrade(buyer);
		
		String json = jsonMapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d678/trade").content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$..message").value("Item value for the trade are different. Currently: 10 / 6"))
				.andDo(print());
	}
	
	@Test
	public void shouldPerformTrade() throws Exception {
		TradeDTO dto = new TradeDTO();
		dto.setBuyerId("58eaabaa2b43926980b5d650");
		
		SurvivorInventoryDTO seller = new SurvivorInventoryDTO();
		seller.setMedication(3);
		dto.setSellerTrade(seller);
		
		SurvivorInventoryDTO buyer = new SurvivorInventoryDTO();
		buyer.setFood(2);
		dto.setBuyerTrade(buyer);
		
		String json = jsonMapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d678/trade").content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$..message").value("Trade succesful."))
				.andDo(document("survivors/trade",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.andDo(print());
	}
	
	@Test
	public void shouldNotPerformTradeWithNonExistentSurvivor() throws Exception {
		TradeDTO dto = new TradeDTO();
		dto.setBuyerId(new ObjectId().toHexString());
		
		SurvivorInventoryDTO seller = new SurvivorInventoryDTO();
		seller.setMedication(3);
		dto.setSellerTrade(seller);
		
		SurvivorInventoryDTO buyer = new SurvivorInventoryDTO();
		buyer.setFood(2);
		dto.setBuyerTrade(buyer);
		
		String json = jsonMapper.writeValueAsString(dto);
		
		MockHttpServletRequestBuilder requestBuilder = post(BASE_URL + "58eaabaa2b43926980b5d678/trade").content(json)
				.contentType(MediaType.APPLICATION_JSON);

		super.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$..message").value("One of the given IDs could not be found. Currently: 58eaabaa2b43926980b5d678 / " + dto.getBuyerId()))
				.andDo(print());
	}

}
