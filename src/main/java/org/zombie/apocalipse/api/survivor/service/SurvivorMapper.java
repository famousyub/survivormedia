package org.zombie.apocalipse.api.survivor.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.zombie.apocalipse.api.core.exception.InvalidParamsException;
import org.zombie.apocalipse.api.core.mapper.AbstractGenericMapper;
import org.zombie.apocalipse.api.infection.dto.InfectionReportDTO;
import org.zombie.apocalipse.api.invetory.dto.InventoryDTO;
import org.zombie.apocalipse.api.survivor.dto.SurvivorDTO;
import org.zombie.apocalipse.api.survivor.dto.SurvivorInventoryDTO;
import org.zombie.apocalipse.api.survivor.model.Position;
import org.zombie.apocalipse.api.survivor.model.Survivor;

/**
 * mapper for the {@link Survivor} entity and {@link SurvivorDTO} dto
 * 
 * @author Itair Miguel
 *
 */
@Component
public class SurvivorMapper extends AbstractGenericMapper<Survivor, SurvivorDTO> {

	public SurvivorMapper(ModelMapper mapper) {
		super(mapper, Survivor.class, SurvivorDTO.class);
	}

	private static final int LATITUDE = 0;
	private static final int LONGITUDE = 1;

	@Override
	public SurvivorDTO toDto(Survivor entity) {
		SurvivorDTO dto = super.toDto(entity);
		dto.setLastLocation(entity.getPosition().getCoordinates().toArray(new Double[0]));

		if (entity.getInventory() != null && entity.getInventory().length > 0) {
			dto.setInventory(this.toSurvivorInventory(entity.getInventory()[0].getItems()));
		}

		return dto;
	}

	@Override
	public Survivor toEntity(SurvivorDTO dto) {
		Survivor survivor = super.toEntity(dto);
		if (dto.getLastLocation() != null && dto.getLastLocation().length > 0) {
			Position point = new Position(dto.getLastLocation()[LONGITUDE], dto.getLastLocation()[LATITUDE]);
			survivor.setPosition(point);
		}

		return survivor;
	}

	/**
	 * sets the {@link InventoryDTO} from the {@link SurvivorDTO}
	 * 
	 * @param dto
	 *            {@link SurvivorDTO} containing the inventory items
	 * @param survivorId
	 *            bson ObjectId
	 * @return {@link InventoryDTO} representing the inventory
	 * @throws InvalidParamsException
	 *             Inventory is null or has a malformed string
	 */
	public InventoryDTO toInventoryDto(SurvivorDTO dto) {
		SurvivorInventoryDTO declaredInventory = dto.getInventory();

		if (declaredInventory == null) {
			throw new InvalidParamsException("Your inventory may not be null.");
		}

		InventoryDTO inventory = new InventoryDTO();
		inventory.setItems(dto.getInventory().getMappedInventory());
		inventory.setSurvivorId(new ObjectId(dto.getId()));

		return inventory;
	}

	/**
	 * set the Map items to a representantion of the survivor's inventory
	 * 
	 * @param inventory
	 *            Map<K,V> with the inventory
	 * @return SurvivorInventoryDTO survivor's inventory
	 */
	public SurvivorInventoryDTO toSurvivorInventory(Map<String, Integer> items) {
		SurvivorInventoryDTO dto = new SurvivorInventoryDTO();
		dto.setAmmunition(items.get(SurvivorInventory.AMMUNITION.name()));
		dto.setWater(items.get(SurvivorInventory.WATER.name()));
		dto.setFood(items.get(SurvivorInventory.FOOD.name()));
		dto.setMedication(items.get(SurvivorInventory.MEDICATION.name()));

		return dto;
	}

	/**
	 * set the infeciton report from the given suspect infected and reporter
	 * 
	 * @param infectedSuspectId
	 *            id of the survivor to be reported
	 * @param reporter
	 *            id of the survivor that is reporting
	 * @return {@link InfectionReportDTO} the dto of the report
	 */
	public InfectionReportDTO toInfectionReportDTO(ObjectId infectedSuspectId, ObjectId reporter) {
		InfectionReportDTO dto = new InfectionReportDTO();
		dto.setReported(infectedSuspectId);
		dto.setReporter(reporter);
		dto.setdTEvent(new Date());

		return dto;
	}

	/**
	 * set a map based on the inventory
	 * 
	 * @param inventory
	 *            inventory
	 * @return map of items in the inventory
	 */
	public Map<String, Integer> survivorInventorytoMap(SurvivorInventoryDTO inventory) {
		Map<String, Integer> items = new HashMap<String, Integer>();
		items.put(SurvivorInventory.AMMUNITION.name(), inventory.getAmmunition());
		items.put(SurvivorInventory.WATER.name(), inventory.getWater());
		items.put(SurvivorInventory.FOOD.name(), inventory.getFood());
		items.put(SurvivorInventory.MEDICATION.name(), inventory.getMedication());

		return items;
	}
	
}
