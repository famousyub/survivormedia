package org.zombie.apocalipse.api.invetory.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.zombie.apocalipse.api.invetory.model.Inventory;

/**
 * inventory repository. Nothing more to add I think.
 * 
 * @author Itair Miguel
 *
 */
public interface InventoryRepository extends MongoRepository<Inventory, String> {

	/**
	 * gets the inventory for the survivor
	 * 
	 * @param survivorId
	 *            ID of the survivor, owner of this inventory
	 * @return inventory of the survivor
	 */
	Inventory findBySurvivorId(String survivorId);

	/**
	 * updates the inventory
	 * 
	 * @param inventory
	 *            inventory to be updated
	 */
	void update(Inventory inventory);

	/**
	 * gets a list of inventories by surivor's ids
	 * 
	 * @param survivorsIds
	 *            ids of the survivors
	 * @return list of inventories
	 */
	List<Inventory> getInventoriesBySurvivorsIds(List<ObjectId> survivorsIds);
}
