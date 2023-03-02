package org.zombie.apocalipse.api.reports;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.zombie.apocalipse.api.report.dto.InventoryReportsDTO;
import org.zombie.apocalipse.api.report.service.SurvivorReportsUtils;
import org.zombie.apocalipse.api.survivor.service.SurvivorInventory;

public class SurvivorReportsUtilsTest {

	Map<String, Integer> items;
	Map<String, Integer> items2;
	int expectedAmmo;
	int expectedFood;
	int expectedMeds;
	int expectedWater;

	@Before
	public void setUp() {
		items = new HashMap<>();
		items.put(SurvivorInventory.AMMUNITION.name(), 678);
		items.put(SurvivorInventory.FOOD.name(), 356);
		items.put(SurvivorInventory.MEDICATION.name(), 203);
		items.put(SurvivorInventory.WATER.name(), 75);

		items2 = new HashMap<>();
		items2.put(SurvivorInventory.AMMUNITION.name(), 345);
		items2.put(SurvivorInventory.FOOD.name(), 123);
		items2.put(SurvivorInventory.MEDICATION.name(), 634);
		items2.put(SurvivorInventory.WATER.name(), 13);

		expectedAmmo = items.get(SurvivorInventory.AMMUNITION.name()) + items2.get(SurvivorInventory.AMMUNITION.name());
		expectedFood = items.get(SurvivorInventory.FOOD.name()) + items2.get(SurvivorInventory.FOOD.name());
		expectedMeds = items.get(SurvivorInventory.MEDICATION.name()) + items2.get(SurvivorInventory.MEDICATION.name());
		expectedWater = items.get(SurvivorInventory.WATER.name()) + items2.get(SurvivorInventory.WATER.name());
	}

	@Test
	public void shouldSetAverageItemsPerSurvivor() {
		InventoryReportsDTO dto = new InventoryReportsDTO();
		int numberOfSurvivors = 233;

		Double expectedAmmo = items.get(SurvivorInventory.AMMUNITION.name()) / (numberOfSurvivors * 1.0);
		Double expectedFood = items.get(SurvivorInventory.FOOD.name()) / (numberOfSurvivors * 1.0);
		Double expectedMeds = items.get(SurvivorInventory.MEDICATION.name()) / (numberOfSurvivors * 1.0);
		Double expectedWater = items.get(SurvivorInventory.WATER.name()) / (numberOfSurvivors * 1.0);

		SurvivorReportsUtils.setAverageItemPerSurvivor(dto, items, numberOfSurvivors);

		assertThat(dto.getAmmunitionPerSurvivor(), equalTo(expectedAmmo));
		assertThat(dto.getFoodPerSurvivor(), equalTo(expectedFood));
		assertThat(dto.getWaterPerSurvivor(), equalTo(expectedWater));
		assertThat(dto.getMedicationPerSurvivor(), equalTo(expectedMeds));
	}

	@Test
	public void shouldSumItems() {
		Map<String, Integer> totalMap = new HashMap<>();

		SurvivorReportsUtils.sumItems(totalMap, items);
		SurvivorReportsUtils.sumItems(totalMap, items2);

		assertThat(totalMap.get(SurvivorInventory.AMMUNITION.name()), equalTo(expectedAmmo));
		assertThat(totalMap.get(SurvivorInventory.FOOD.name()), equalTo(expectedFood));
		assertThat(totalMap.get(SurvivorInventory.WATER.name()), equalTo(expectedWater));
		assertThat(totalMap.get(SurvivorInventory.MEDICATION.name()), equalTo(expectedMeds));
	}

	@Test
	public void shouldSumPointsLost() {
		Integer pointsLost = 0;

		int ammo = expectedAmmo * SurvivorInventory.AMMUNITION.getValue();
		int food = expectedFood * SurvivorInventory.FOOD.getValue();
		int meds = expectedMeds * SurvivorInventory.MEDICATION.getValue();
		int water = expectedWater * SurvivorInventory.WATER.getValue();

		Integer expectedPointsLost = ammo + food + meds + water;

		pointsLost = SurvivorReportsUtils.sumPointsLost(pointsLost, items);
		pointsLost = SurvivorReportsUtils.sumPointsLost(pointsLost, items2);

		assertThat(pointsLost, equalTo(expectedPointsLost));
	}

}
