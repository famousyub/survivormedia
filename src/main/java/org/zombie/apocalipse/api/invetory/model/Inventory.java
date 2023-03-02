package org.zombie.apocalipse.api.invetory.model;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.zombie.apocalipse.api.core.model.AbstractModel;

/***
 * Represents the document in the collection in the MongoDB
 * @author Itair Miguel
 *
 */
@Document(collection="inventories")
public class Inventory extends AbstractModel {

	private Map<String, Integer> items;
	private ObjectId survivorId;

	public Map<String, Integer> getItems() {
		return items;
	}

	public void setItems(Map<String, Integer> items) {
		this.items = items;
	}

	public ObjectId getSurvivorId() {
		return survivorId;
	}

	public void setSurvivorId(ObjectId survivorId) {
		this.survivorId = survivorId;
	}

}
