package org.zombie.apocalipse.api.survivor.model;

import java.util.Arrays;
import java.util.List;

public class Position {
	
	private final String type = "Point";
	
	private double[] coordinates;
	
	public Position() {
		
	}
	
	public Position(double lon, double lat) {
		this.coordinates = new double[]{lon, lat};
	}
	
	public List<Double> getCoordinates() {
		return Arrays.asList(Double.valueOf(this.coordinates[0]), Double.valueOf(this.coordinates[1]));
	}

	public String getType() {
		return type;
	}
}
