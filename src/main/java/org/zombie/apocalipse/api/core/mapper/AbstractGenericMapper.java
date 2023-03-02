package org.zombie.apocalipse.api.core.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractGenericMapper<E, D> implements GenericMapper<E, D> {

	private ModelMapper mapper;

	private Class<E> entity;
	private Class<D> dtoClass;

	@Autowired
	public AbstractGenericMapper(ModelMapper mapper, Class<E> entity, Class<D> dtoClass) {
		this.mapper = mapper;
		this.entity = entity;
		this.dtoClass = dtoClass;
	}

	@Override
	public D toDto(E entity) {
		return mapper.map(entity, dtoClass);
	}

	@Override
	public E toEntity(D dto) {
		return mapper.map(dto, entity);
	}

	@Override
	public List<D> toDtoList(List<E> entities) {
		return entities.stream().map(e -> this.toDto(e)).collect(Collectors.toList());
	}

	@Override
	public List<E> toEntityList(List<D> dtos) {
		return dtos.stream().map(e -> this.toEntity(e)).collect(Collectors.toList());
	}
}
