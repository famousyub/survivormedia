package org.zombie.apocalipse.api.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zombie.apocalipse.api.report.dto.InventoryReportsDTO;
import org.zombie.apocalipse.api.survivor.service.SurvivorInventory;

public class SurvivorReportsUtils {
	
	public static InventoryReportsDTO setAverage(InventoryReportsDTO dto, List<Map<String, Integer>> itemsList) {
		Map<String, Integer> totaItems = new HashMap<>();

		itemsList.forEach(e -> sumItems(totaItems, e));
		
		setAverageItemPerSurvivor(dto, totaItems, itemsList.size());

		return dto;
	}

	public static InventoryReportsDTO setAverageItemPerSurvivor(InventoryReportsDTO dto, Map<String, Integer> items,
			Integer numberOfSurvivors) {

		Integer ammo = items.get(SurvivorInventory.AMMUNITION.name());
		Integer food = items.get(SurvivorInventory.FOOD.name());
		Integer water = items.get(SurvivorInventory.WATER.name());
		Integer meds = items.get(SurvivorInventory.MEDICATION.name());
		
		if(ammo != null && ammo > 0) {
			dto.setAmmunitionPerSurvivor(ammo / (numberOfSurvivors * 1.0));
		}
		if(food != null && food > 0) {
			dto.setFoodPerSurvivor(food / (numberOfSurvivors * 1.0));
		}
		if(water != null && water > 0) {
			dto.setWaterPerSurvivor(water / (numberOfSurvivors * 1.0));
		}
		if(meds != null && meds > 0) {
			dto.setMedicationPerSurvivor(meds / (numberOfSurvivors * 1.0));
		}
		
		return dto;
	}

	public static void sumItems(Map<String, Integer> totalMap, Map<String, Integer> items) {
		for (Map.Entry<String, Integer> entry : items.entrySet()) {
			Integer value = totalMap.get(entry.getKey());
			if (value != null && value > 0) {
				totalMap.put(entry.getKey(), value + entry.getValue());
				continue;
			}
			totalMap.put(entry.getKey(), entry.getValue());
		}
	}
	
	public static Integer sumPointsLost(Integer pointsLost, Map<String, Integer> items) {
		for (Map.Entry<String, Integer> entry : items.entrySet()) {
			if (entry.getValue() > 0) {
				pointsLost += (SurvivorInventory.getValue(entry.getKey()) * entry.getValue());
			}
		}
		return pointsLost;
	}
	
}
