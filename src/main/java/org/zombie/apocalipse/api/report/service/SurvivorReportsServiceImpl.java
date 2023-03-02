package org.zombie.apocalipse.api.report.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zombie.apocalipse.api.infection.service.InfectionReportService;
import org.zombie.apocalipse.api.invetory.service.InventoryService;
import org.zombie.apocalipse.api.report.dto.InventoryReportsDTO;
import org.zombie.apocalipse.api.report.dto.SurvivorReportsDTO;
import org.zombie.apocalipse.api.survivor.service.SurvivorService;

@Service
public class SurvivorReportsServiceImpl implements SurvivorReportsService {

	private SurvivorService survivor;
	private InventoryService inventory;
	@SuppressWarnings("unused")
	private InfectionReportService infection;

	@Autowired
	public SurvivorReportsServiceImpl(SurvivorService survivor, InventoryService inventory,
			InfectionReportService infection) {
		this.survivor = survivor;
		this.inventory = inventory;
		this.infection = infection;
	}

	@Override
	public SurvivorReportsDTO percentageOfInfectedSurvivorReport() {
		SurvivorReportsDTO dto = new SurvivorReportsDTO();

		Long numberOfSurvivors = survivor.getTotalSurvivors();

		Integer totalInfected = survivor.totalInfectedSurvivors();
		dto.setPercentage((100d / numberOfSurvivors) * totalInfected);

		return dto;
	}

	@Override
	public SurvivorReportsDTO percentageOfNonInfectedSurvivorReport() {
		SurvivorReportsDTO dto = new SurvivorReportsDTO();

		Long numberOfSurvivors = survivor.getTotalSurvivors();

		Integer totalNonInfected = survivor.totalNonlInfectedSurvivors();
		dto.setPercentage((100d / numberOfSurvivors) * totalNonInfected);

		return dto;
	}

	@Override
	public InventoryReportsDTO inventoryPointsLostReport() {
		return this.inventoryReportByPointsLost(survivor.getInfectedSurvivorsId());
	}

	@Override
	public InventoryReportsDTO inventoryAverageAmountOfItemsPerSurvivorReport() {
		return this.averageAmountPerSurvivor(survivor.getNonInfectedSurvivorsId());
	}

	private InventoryReportsDTO averageAmountPerSurvivor(List<String> infectedSurvivorsId) {
		InventoryReportsDTO dto = new InventoryReportsDTO();
		List<Map<String, Integer>> inventoryItems;

		if (infectedSurvivorsId == null) {
			dto.setAmmunitionPerSurvivor(0.0);
			dto.setFoodPerSurvivor(0.0);
			dto.setWaterPerSurvivor(0.0);
			dto.setMedicationPerSurvivor(0.0);
			return dto;
		}

		inventoryItems = inventory.getInventoryItemsBySurvivorsIds(infectedSurvivorsId);

		SurvivorReportsUtils.setAverage(dto, inventoryItems);

		return dto;
	}

	private InventoryReportsDTO inventoryReportByPointsLost(List<String> infectedSurvivorsId) {
		InventoryReportsDTO dto = new InventoryReportsDTO();
		List<Map<String, Integer>> inventoryItems;

		if (infectedSurvivorsId == null) {
			dto.setPointsLost(0);
			return dto;
		}

		inventoryItems = inventory.getInventoryItemsBySurvivorsIds(infectedSurvivorsId);

		if (inventoryItems != null && inventoryItems.size() > 0) {
			Integer pointsLost = 0;
			for (Map<String, Integer> items : inventoryItems) {
				pointsLost = SurvivorReportsUtils.sumPointsLost(pointsLost, items);
			}
			dto.setPointsLost(pointsLost);
		}

		return dto;
	}

}
