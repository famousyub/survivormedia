package org.zombie.apocalipse.api.invetory.dto;

import java.util.Map;

import org.bson.types.ObjectId;
import org.zombie.apocalipse.api.core.dto.AbstractDocumentDTO;

/**
 * Data Transfer Object to transit through the layers
 * @author Itair Miguel
 *
 */
public class InventoryDTO extends AbstractDocumentDTO {
	
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
