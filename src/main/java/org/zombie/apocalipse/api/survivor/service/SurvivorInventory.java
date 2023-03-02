package org.zombie.apocalipse.api.survivor.service;

public enum SurvivorInventory {

	WATER(4), FOOD(3), MEDICATION(2), AMMUNITION(1);

	private Integer value;

	SurvivorInventory(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
	
	public static Integer getValue(String name) {
		for (int i = 0; i < values().length; i++) {
			if(values()[i].name().equalsIgnoreCase(name)) {
				return values()[i].getValue();
			}
		}
		return 0;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
