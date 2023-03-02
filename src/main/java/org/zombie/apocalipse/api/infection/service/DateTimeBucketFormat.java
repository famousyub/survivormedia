package org.zombie.apocalipse.api.infection.service;

public enum DateTimeBucketFormat {
	
	HOUR("yyyy-MM-dd HH", "-hour"),
	DAY("yyyy-MM-dd", "-day"),
	MONTH_WEEK("yyyy-MM-WW", "-month_week"),
	MONTH("yyyy-MM", "-month"),
	YEAR_WEEK("yyyy-ww", "-year_week"),
	YEAR("yyyy", "-year");
	
	private String format;
	private String representation;
	
	DateTimeBucketFormat(String format, String representation) {
		this.format = format;
		this.representation = representation;
	}
	
	private static DateTimeBucketFormat[] formats = values();
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getRepresentation() {
		return representation;
	}

	public void setRepresentation(String representation) {
		this.representation = representation;
	}
	
	public static String[] getAllFormats() {
		String[] keys = new String[formats.length];
		for (int i = 0; i < formats.length; i++) {
			keys[i] = formats[i].getFormat();
		}
		return keys;
	}
	
	public static String[] getAllRepresentations() {
		String[] keys = new String[formats.length];
		for (int i = 0; i < formats.length; i++) {
			keys[i] = formats[i].getRepresentation();
		}
		return keys;
	}

}
