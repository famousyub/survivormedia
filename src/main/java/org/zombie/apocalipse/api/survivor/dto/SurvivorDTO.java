package org.zombie.apocalipse.api.survivor.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.zombie.apocalipse.api.core.dto.AbstractDocumentDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Data Transfer Object to transit through the layers
 * 
 * @author Itair Miguel
 *
 */
@JsonInclude(Include.NON_NULL)
public class SurvivorDTO extends AbstractDocumentDTO {

	@NotNull
	private String name;
	@NotNull
	private Integer age;
	@NotNull
	private String gender;

	private Integer contaminationReports;

	@NotEmpty
	private Double[] lastLocation;

	@NotNull
	private SurvivorInventoryDTO inventory;

	@JsonIgnore
	private boolean incrementReports;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getContaminationReports() {
		return contaminationReports;
	}

	public void setContaminationReports(Integer conaminationReports) {
		this.contaminationReports = conaminationReports;
	}

	public Double[] getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Double... lastLocation) {
		this.lastLocation = lastLocation.clone();
	}

	public SurvivorInventoryDTO getInventory() {
		return inventory;
	}

	public void setInventory(SurvivorInventoryDTO inventory) {
		this.inventory = inventory;
	}

	public boolean isIncrementReports() {
		return incrementReports;
	}

	public void setIncrementReports(boolean incrementReports) {
		this.incrementReports = incrementReports;
	}

}
