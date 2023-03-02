package org.zombie.apocalipse.api.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.zombie.apocalipse.api.core.dto.DocumentDTO;
import org.zombie.apocalipse.api.core.exception.ServiceException;
import org.zombie.apocalipse.api.core.mapper.GenericMapper;
import org.zombie.apocalipse.api.core.model.Model;

@Service
public abstract class AbstractCrudService<E extends Model, D extends DocumentDTO, R extends MongoRepository<E, String>, M extends GenericMapper<E, D>>
		implements CrudService<E, D> {

	private R repo;
	private M mapper;
	
	@Autowired
	public AbstractCrudService(R repository, M mapper) {
		this.repo = repository;
		this.mapper = mapper;
	}

	public D create(D dto) {
		E result;
		if((result = repo.save(mapper.toEntity(dto))) == null) {
			throw new ServiceException("Could not save.");
		}
		return mapper.toDto(result);
	}

	public D search(String id) {
		E result = repo.findOne(id);
		return mapper.toDto(result);
	}

	public List<D> searchAll() {
		return repo.findAll().stream().map(e -> mapper.toDto(e)).collect(Collectors.toList());
	}

	public void update(D dto) {

	}

	public void delete(D dto) {
		repo.delete(dto.getId());
	}

}
