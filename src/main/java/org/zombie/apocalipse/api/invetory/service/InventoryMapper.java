package org.zombie.apocalipse.api.invetory.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.zombie.apocalipse.api.core.mapper.AbstractGenericMapper;
import org.zombie.apocalipse.api.invetory.dto.InventoryDTO;
import org.zombie.apocalipse.api.invetory.model.Inventory;

/**
 * the mapper for the {@link Inventory} entity representation and the {@link InventoryDTO} dto.
 * @author Itair Miguel
 *
 */
@Component
public class InventoryMapper extends AbstractGenericMapper<Inventory, InventoryDTO> {

	public InventoryMapper(ModelMapper mapper) {
		super(mapper, Inventory.class, InventoryDTO.class);
	}
	
}
