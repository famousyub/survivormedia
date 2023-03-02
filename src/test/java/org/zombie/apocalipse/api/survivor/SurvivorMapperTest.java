package org.zombie.apocalipse.api.survivor;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.modelmapper.ModelMapper;
import org.zombie.apocalipse.api.core.exception.InvalidParamsException;
import org.zombie.apocalipse.api.infection.dto.InfectionReportDTO;
import org.zombie.apocalipse.api.invetory.dto.InventoryDTO;
import org.zombie.apocalipse.api.survivor.dto.SurvivorDTO;
import org.zombie.apocalipse.api.survivor.dto.SurvivorInventoryDTO;
import org.zombie.apocalipse.api.survivor.model.Position;
import org.zombie.apocalipse.api.survivor.model.Survivor;
import org.zombie.apocalipse.api.survivor.service.SurvivorInventory;
import org.zombie.apocalipse.api.survivor.service.SurvivorMapper;

public class SurvivorMapperTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	private SurvivorMapper mapper;
	
	@Before
	public void setUp() {
		ModelMapper modelMapper = new ModelMapper();
		this.mapper = new SurvivorMapper(modelMapper);
	}

	@Test
	public void shouldMapEntityToDto() {
		Survivor entity = new Survivor();
		entity.setId("124124124124124");
		entity.setAge(34);
		entity.setContaminationReports(2);
		entity.setGender("female");
		entity.setName("Julia Reinolds");
		
		Position point = new Position(-23.09090, -45.90909);
		entity.setPosition(point);
		
		SurvivorDTO dto = mapper.toDto(entity);
		assertThat(dto.getId(), equalTo("124124124124124"));
		assertThat(dto.getAge(), equalTo(34));
		assertThat(dto.getContaminationReports(), equalTo(2));
		assertThat(dto.getGender(), equalTo("female"));
		assertThat(dto.getName(), equalTo("Julia Reinolds"));
		assertThat(dto.getLastLocation(), equalTo(new Double[]{-23.09090, -45.90909}));
	}

	@Test
	public void shouldMapDtoToEntity() {
		SurvivorDTO dto = new SurvivorDTO();
		dto.setId("124124124124124");
		dto.setAge(34);
		dto.setContaminationReports(2);
		dto.setGender("female");
		dto.setName("Julia Reinolds");
		dto.setLastLocation(new Double[]{-45.90909,-23.09090});
		
		Survivor entity = mapper.toEntity(dto);
		assertThat(entity.getId(), equalTo("124124124124124"));
		assertThat(entity.getAge(), equalTo(34));
		assertThat(entity.getContaminationReports(), equalTo(2));
		assertThat(entity.getGender(), equalTo("female"));
		assertThat(entity.getName(), equalTo("Julia Reinolds"));
		assertThat(entity.getPosition().getCoordinates().get(0), equalTo(-23.09090));
		assertThat(entity.getPosition().getCoordinates().get(1), equalTo(-45.90909));
	}
	
	@Test
	public void shouldMapToInventoryDTO() {
		SurvivorDTO dto = new SurvivorDTO();
		dto.setId(new ObjectId().toHexString());
		SurvivorInventoryDTO inv = new SurvivorInventoryDTO();
		inv.setAmmunition(2);
		inv.setFood(1);
		inv.setMedication(3);
		inv.setWater(4);
		dto.setInventory(inv);
		
		InventoryDTO inventory = mapper.toInventoryDto(dto);
		assertThat(inventory.getItems().get(SurvivorInventory.AMMUNITION.name()), equalTo(inv.getAmmunition()));
		assertThat(inventory.getItems().get(SurvivorInventory.FOOD.name()), equalTo(inv.getFood()));
		assertThat(inventory.getItems().get(SurvivorInventory.MEDICATION.name()), equalTo(inv.getMedication()));
		assertThat(inventory.getItems().get(SurvivorInventory.WATER.name()), equalTo(inv.getWater()));
		assertThat(inventory.getSurvivorId(), equalTo(new ObjectId(dto.getId())));
	}
	
	@Test
	public void shouldMapToSurvivorInventory() {
		InventoryDTO inventory = new InventoryDTO();

		Map<String, Integer> items = new HashMap<String, Integer>();
		items.put(SurvivorInventory.AMMUNITION.name(), 12);
		items.put(SurvivorInventory.WATER.name(), 2);
		items.put(SurvivorInventory.FOOD.name(), 7);
		items.put(SurvivorInventory.MEDICATION.name(), 0);
		inventory.setItems(items);
		
		SurvivorDTO dto = new SurvivorDTO();
		dto.setInventory(mapper.toSurvivorInventory(items));
		
		assertThat(dto.getInventory().getAmmunition(), equalTo(items.get(SurvivorInventory.AMMUNITION.name())));
		assertThat(dto.getInventory().getWater(), equalTo(items.get(SurvivorInventory.WATER.name())));
		assertThat(dto.getInventory().getFood(), equalTo(items.get(SurvivorInventory.FOOD.name())));
		assertThat(dto.getInventory().getMedication(), equalTo(items.get(SurvivorInventory.MEDICATION.name())));
	}
	
	@Test
	public void shouldMapToInfectionReport() {
		ObjectId infectedSuspectId = new ObjectId();
		ObjectId reporter = new ObjectId();
		
		InfectionReportDTO dto = mapper.toInfectionReportDTO(infectedSuspectId, reporter);
		
		assertThat(dto.getReported(), equalTo(infectedSuspectId));
		assertThat(dto.getReporter(), equalTo(reporter));
	}
	
	@Test
	public void shouldNotAllowNullInventory() {
		expectedEx.expect(InvalidParamsException.class);
		expectedEx.expectMessage("Your inventory may not be null.");
		
		SurvivorDTO dto = new SurvivorDTO();
		dto.setId(new ObjectId().toHexString());
		
		mapper.toInventoryDto(dto);
	}
}
