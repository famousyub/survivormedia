package org.zombie.apocalipse.api.survivor.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SurvivorInventoryDTO {

	private Integer water = 0;
	private Integer food = 0;
	private Integer medication = 0;
	private Integer ammunition = 0;

	@JsonIgnore
	private Map<String, Integer> mappedInventory = new HashMap<String, Integer>();
	
	public Integer getWater() {
		return water;
	}

	public void setWater(Integer water) {
		this.water = water;
		this.setMap("WATER", water);
	}

	public Integer getFood() {
		return food;
	}

	public void setFood(Integer food) {
		this.food = food;
		this.setMap("FOOD", food);
	}

	public Integer getMedication() {
		return medication;
	}

	public void setMedication(Integer medication) {
		this.medication = medication;
		this.setMap("MEDICATION", medication);
	}

	public Integer getAmmunition() {
		return ammunition;
	}

	public void setAmmunition(Integer ammunition) {
		this.ammunition = ammunition;
		this.setMap("AMMUNITION", ammunition);
	}

	public Map<String, Integer> getMappedInventory() {
		return mappedInventory;
	}

	public void setMappedInventory(Map<String, Integer> mappedInventory) {
		this.mappedInventory = mappedInventory;
	}
	
	private void setMap(String key, Integer value){
		this.mappedInventory.put(key, value);
	}

}
