package org.zombie.apocalipse.api.report.service;

import org.zombie.apocalipse.api.report.dto.InventoryReportsDTO;
import org.zombie.apocalipse.api.report.dto.SurvivorReportsDTO;

public interface SurvivorReportsService {

	/**
	 * gets the percentage of infected survivors
	 * 
	 * @return the percentage
	 */
	SurvivorReportsDTO percentageOfInfectedSurvivorReport();

	/**
	 * gets the percentage of non infected survivors
	 * 
	 * @return the percentage
	 */
	SurvivorReportsDTO percentageOfNonInfectedSurvivorReport();

	/**
	 * gets the total of points lost due to infected survivors
	 * 
	 * @return points lost
	 */
	InventoryReportsDTO inventoryPointsLostReport();

	/**
	 * returns the average amount of items per survivor
	 * 
	 * @return the average for each type of item
	 */
	InventoryReportsDTO inventoryAverageAmountOfItemsPerSurvivorReport();

}
