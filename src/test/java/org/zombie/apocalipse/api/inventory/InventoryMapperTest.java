package org.zombie.apocalipse.api.inventory;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.zombie.apocalipse.api.invetory.dto.InventoryDTO;
import org.zombie.apocalipse.api.invetory.model.Inventory;
import org.zombie.apocalipse.api.invetory.service.InventoryMapper;
import org.zombie.apocalipse.api.survivor.service.SurvivorInventory;

public class InventoryMapperTest {
	
	private InventoryMapper mapper;
	private Map<String, Integer> items;
	
	@Before
	public void setUp() {
		ModelMapper modelMapper = new ModelMapper();
		this.mapper = new InventoryMapper(modelMapper);
		items = new HashMap<String, Integer>();
		items.put(SurvivorInventory.AMMUNITION.name(), 4);
		items.put(SurvivorInventory.WATER.name(), 1);
		items.put(SurvivorInventory.FOOD.name(), 0);
		items.put(SurvivorInventory.MEDICATION.name(), 2);
	}

	@Test
	public void shouldMapEntityToDto() {
		Inventory entity = new Inventory();
		entity.setId("1424124124");
		entity.setSurvivorId(new ObjectId("58ea61542b43926ff026bafa"));
		entity.setItems(items);
		
		InventoryDTO inventory = mapper.toDto(entity);
		
		assertThat(inventory.getItems().get(SurvivorInventory.AMMUNITION.name()), equalTo(4));
		assertThat(inventory.getItems().get(SurvivorInventory.FOOD.name()), equalTo(0));
		assertThat(inventory.getItems().get(SurvivorInventory.MEDICATION.name()), equalTo(2));
		assertThat(inventory.getItems().get(SurvivorInventory.WATER.name()), equalTo(1));
		assertThat(inventory.getSurvivorId(), equalTo(entity.getSurvivorId()));
	}

	@Test
	public void shouldMapDtoToEntity() {
		InventoryDTO dto = new InventoryDTO();
		dto.setItems(items);
		dto.setSurvivorId(new ObjectId("58ea61542b43926ff026bafa"));
		
		Inventory entity = mapper.toEntity(dto);
		
		assertThat(entity.getItems().get(SurvivorInventory.AMMUNITION.name()), equalTo(4));
		assertThat(entity.getItems().get(SurvivorInventory.FOOD.name()), equalTo(0));
		assertThat(entity.getItems().get(SurvivorInventory.MEDICATION.name()), equalTo(2));
		assertThat(entity.getItems().get(SurvivorInventory.WATER.name()), equalTo(1));
		assertThat(entity.getSurvivorId(), equalTo(dto.getSurvivorId()));
	}
	
}
