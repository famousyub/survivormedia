package org.zombie.apocalipse.api.core.dto;

/*
 * contract of all DTOs
 */
public interface DocumentDTO {
	
	String id = null;
	
	/*
	 * sets the id
	 */
	void setId(String id);
	
	/*
	 * gets the id
	 */
	String getId();
}
