package org.zombie.apocalipse.api.invetory.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.zombie.apocalipse.api.core.exception.NotFoundException;
import org.zombie.apocalipse.api.core.mongo.QueryBuilder;
import org.zombie.apocalipse.api.core.mongo.UpdateBuilder;
import org.zombie.apocalipse.api.core.repository.AbstractCustomRepository;
import org.zombie.apocalipse.api.invetory.model.Inventory;
import org.zombie.apocalipse.api.survivor.service.SurvivorInventory;

@Repository
public class InventoryRepositoryImpl extends AbstractCustomRepository<Inventory> {

	public InventoryRepositoryImpl(MongoTemplate temp) {
		super(temp, Inventory.class);
	}

	public void update(Inventory inventory) {
		Query query = new QueryBuilder().is("survivorId", inventory.getSurvivorId()).build();

		Update update = new UpdateBuilder()
				.set("items.WATER", inventory.getItems().get(SurvivorInventory.WATER.name()))
				.set("items.FOOD", inventory.getItems().get(SurvivorInventory.FOOD.name()))
				.set("items.MEDICATION", inventory.getItems().get(SurvivorInventory.MEDICATION.name()))
				.set("items.AMMUNITION", inventory.getItems().get(SurvivorInventory.AMMUNITION.name())).build();

		boolean isOk = super.update(query, update);

		if (!isOk) {
			throw new NotFoundException(
					"Not possible to update the inventory. No inventory was found for the given survivor ID.");
		}
	}
	
	public List<Inventory> getInventoriesBySurvivorsIds(List<ObjectId> survivorsIds) {
		Query query = new QueryBuilder().in("survivorId", survivorsIds).build();
		return super.findByQuery(query);
	}
}
