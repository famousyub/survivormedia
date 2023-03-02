package org.zombie.apocalipse.api.core.model;

/*
 * contract between all model classes
 */
public interface Model {
	
	String id = null;
	
	/*
	 * sets the id
	 */
	void setId(String id);
	
	/*
	 * sets the id
	 */
	String getId();
}
