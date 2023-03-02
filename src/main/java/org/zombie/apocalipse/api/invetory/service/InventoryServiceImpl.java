package org.zombie.apocalipse.api.invetory.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zombie.apocalipse.api.core.exception.NotFoundException;
import org.zombie.apocalipse.api.core.service.AbstractCrudService;
import org.zombie.apocalipse.api.invetory.dto.InventoryDTO;
import org.zombie.apocalipse.api.invetory.model.Inventory;
import org.zombie.apocalipse.api.invetory.repository.InventoryRepository;

/**
 * Service layer to the survivor inventory
 * 
 * @author Itair Miguel
 *
 */
@Service
public class InventoryServiceImpl extends
		AbstractCrudService<Inventory, InventoryDTO, InventoryRepository, InventoryMapper> implements InventoryService {

	private InventoryRepository repo;
	private InventoryMapper mapper;

	@Autowired
	public InventoryServiceImpl(InventoryRepository repo, InventoryMapper mapper) {
		super(repo, mapper);
		this.repo = repo;
		this.mapper = mapper;
	}

	@Override
	public InventoryDTO getSurvivorInventory(String survivorId) {
		Inventory inventory;
		if ((inventory = repo.findBySurvivorId(survivorId)) == null) {
			throw new NotFoundException("No inventories were found.");
		}
		return mapper.toDto(inventory);
	}
	
	@Override
	public void update(InventoryDTO dto) {
		repo.update(mapper.toEntity(dto));
	}

	@Override
	public List<Map<String, Integer>> getInventoryItemsBySurvivorsIds(List<String> ids) {
		List<ObjectId> objectIds = ids.stream().map(e -> new ObjectId(e)).collect(Collectors.toList());
		
		List<Inventory> inventories = repo.getInventoriesBySurvivorsIds(objectIds);
		
		List<Map<String, Integer>> mappedList = null;
		
		if(inventories != null) {
			return inventories.stream().map(e -> e.getItems()).collect(Collectors.toList());
		}
		
		return mappedList;
	}
}
