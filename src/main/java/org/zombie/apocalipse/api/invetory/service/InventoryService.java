package org.zombie.apocalipse.api.invetory.service;

import java.util.List;
import java.util.Map;

import org.zombie.apocalipse.api.core.exception.NotFoundException;
import org.zombie.apocalipse.api.core.service.CrudService;
import org.zombie.apocalipse.api.invetory.dto.InventoryDTO;
import org.zombie.apocalipse.api.invetory.model.Inventory;

/**
 * contract to the service layer
 * 
 * @author Itair Miguel
 *
 */
public interface InventoryService extends CrudService<Inventory, InventoryDTO> {

	/**
	 * gets the inventory for the survivor
	 * 
	 * @param survivorId
	 *            ID of the survivor, owner of this inventory
	 * @return {@link InventoryDTO} representing the inventory of the survivor
	 * @throws NotFoundException
	 *             when no record is found for the given survivorId
	 */
	InventoryDTO getSurvivorInventory(String survivorId) throws NotFoundException;

	/**
	 * gets a list of items by survivor
	 * 
	 * @param ids
	 *            survivor ids
	 * @return a list of the mapped items by survivor
	 */
	List<Map<String, Integer>> getInventoryItemsBySurvivorsIds(List<String> ids);
	
}
