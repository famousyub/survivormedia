package org.zombie.apocalipse.api.survivor.service;

import java.util.List;

import org.zombie.apocalipse.api.core.service.CrudService;
import org.zombie.apocalipse.api.survivor.dto.SurvivorDTO;
import org.zombie.apocalipse.api.survivor.dto.TradeDTO;
import org.zombie.apocalipse.api.survivor.model.Survivor;

/**
 * contract of service layer related to the survivor
 * 
 * @author Itair Miguel
 *
 */
public interface SurvivorService extends CrudService<Survivor, SurvivorDTO> {

	/**
	 * updates the infection counter for the given infected name
	 * 
	 * @param reporterId
	 *            the ID of the reporter
	 * @param infectedName
	 *            name of the infected survivor
	 */
	void updateInfectionCounter(String reporterId, String infectedName);

	/**
	 * trade items between survivors
	 * 
	 * @param sellerId
	 *            id of the seller
	 * @param trade
	 *            the {@link TradeDTO} with information on the trade
	 */
	void trade(String sellerId, TradeDTO trade);

	/**
	 * gets the total of infected survivors
	 * 
	 * @return the total
	 */
	Integer totalInfectedSurvivors();

	/**
	 * gets the total of non infected survivors
	 * 
	 * @return the total
	 */
	Integer totalNonlInfectedSurvivors();

	/**
	 * returns a list with all ids belonging to infected survivors
	 * 
	 * @return
	 */
	List<String> getInfectedSurvivorsId();

	/**
	 * returns a list with all ids belonging to non infected survivors
	 * 
	 * @return
	 */
	List<String> getNonInfectedSurvivorsId();

	/**
	 * returns the total count of the survivors
	 * 
	 * @return numberOfSurvivors
	 */
	Long getTotalSurvivors();
}
