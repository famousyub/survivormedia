package org.zombie.apocalipse.api.core.dto;

/*
 * implements the DocumentDTO
 */
public abstract class AbstractDocumentDTO implements DocumentDTO {

	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
}
