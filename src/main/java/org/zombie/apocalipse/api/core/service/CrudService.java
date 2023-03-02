package org.zombie.apocalipse.api.core.service;

import java.util.List;

import org.zombie.apocalipse.api.core.dto.DocumentDTO;
import org.zombie.apocalipse.api.core.model.Model;

public interface CrudService<E extends Model, D extends DocumentDTO> {
	
	D create(D dto);
	
	D search(String id);
	
	List<D> searchAll();
	
	void update(D dto);
	
	void delete(D dto);
	
}
