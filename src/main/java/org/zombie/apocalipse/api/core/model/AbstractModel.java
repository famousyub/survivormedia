package org.zombie.apocalipse.api.core.model;

import org.springframework.data.annotation.Id;

/*
 * implements the Model
 */
public abstract class AbstractModel implements Model {

	@Id
	protected String id;
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

}
