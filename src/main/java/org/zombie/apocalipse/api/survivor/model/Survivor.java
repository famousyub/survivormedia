package org.zombie.apocalipse.api.survivor.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.zombie.apocalipse.api.core.model.AbstractModel;
import org.zombie.apocalipse.api.invetory.model.Inventory;

/**
 * Represents the survivor document in the collection in mongoDB.
 * 
 * @author Itair Miguel
 *
 */
@Document(collection = "survivors")
public class Survivor extends AbstractModel {

	private String name;
	private Integer age;
	private String gender;
	private Position position;
	private Integer contaminationReports = 0;
	private Inventory[] inventory;

	@Transient
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

	public void setContaminationReports(Integer contaminationReports) {
		this.contaminationReports = contaminationReports;
	}

	public Inventory[] getInventory() {
		return inventory;
	}

	public void setInventory(Inventory[] inventory) {
		this.inventory = inventory.clone();
	}

	public boolean isIncrementReports() {
		return incrementReports;
	}

	public void setIncrementReports(boolean incrementReports) {
		this.incrementReports = incrementReports;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
