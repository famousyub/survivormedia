package org.zombie.apocalipse.api.core.mapper;

import java.util.List;

/**
 * Contract to the whole mapping stuff
 * @author Itair Miguel
 *
 * @param <E> entity class
 * @param <D> dto class
 */
public interface GenericMapper<E, D> {

	/**
	 * maps to the given dto
	 * 
	 * @param entity
	 *            item to map from
	 * @return mapped dto
	 */
	D toDto(E entity);

	/**
	 * maps to the given entity
	 * 
	 * @param dto
	 *            item to map from
	 * @return mapped entity
	 */
	E toEntity(D dto);

	/**
	 * maps to list of dtos
	 * 
	 * @param entities
	 *            list of items to map from
	 * @return mapped list of dtos
	 */
	List<D> toDtoList(List<E> entities);

	/**
	 * maps to list of dtos
	 * 
	 * @param dtos
	 *            list of items to map from
	 * @return mapped list of entities
	 */
	List<E> toEntityList(List<D> dtos);
}
