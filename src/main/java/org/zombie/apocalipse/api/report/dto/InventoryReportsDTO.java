package org.zombie.apocalipse.api.report.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class InventoryReportsDTO {

	private Double waterPerSurvivor;
	private Double ammunitionPerSurvivor;
	private Double foodPerSurvivor;
	private Double medicationPerSurvivor;
	private Integer pointsLost;

	public Double getWaterPerSurvivor() {
		return waterPerSurvivor;
	}

	public void setWaterPerSurvivor(Double waterPerSurvivor) {
		this.waterPerSurvivor = waterPerSurvivor;
	}

	public Double getAmmunitionPerSurvivor() {
		return ammunitionPerSurvivor;
	}

	public void setAmmunitionPerSurvivor(Double ammunitionPerSurvivor) {
		this.ammunitionPerSurvivor = ammunitionPerSurvivor;
	}

	public Double getFoodPerSurvivor() {
		return foodPerSurvivor;
	}

	public void setFoodPerSurvivor(Double foodPerSurvivor) {
		this.foodPerSurvivor = foodPerSurvivor;
	}

	public Double getMedicationPerSurvivor() {
		return medicationPerSurvivor;
	}

	public void setMedicationPerSurvivor(Double medicationPerSurvivor) {
		this.medicationPerSurvivor = medicationPerSurvivor;
	}

	public Integer getPointsLost() {
		return pointsLost;
	}

	public void setPointsLost(Integer pointsLost) {
		this.pointsLost = pointsLost;
	}

}
