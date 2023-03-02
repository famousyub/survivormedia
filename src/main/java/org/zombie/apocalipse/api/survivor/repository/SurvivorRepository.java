package org.zombie.apocalipse.api.survivor.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.zombie.apocalipse.api.core.exception.NotFoundException;
import org.zombie.apocalipse.api.core.repository.CustomRepository;
import org.zombie.apocalipse.api.survivor.model.Survivor;

/**
 * repository for the survivor
 * 
 * @author Itair Miguel
 *
 */
@Repository
public interface SurvivorRepository extends MongoRepository<Survivor, String>, CustomRepository<Survivor> {

	/**
	 * list all the survivors and their inventory
	 * 
	 * @return list of survivors
	 */
	List<Survivor> getAllSurvivors();

	/**
	 * updates the survivor
	 * 
	 * @param survivor
	 *            survivor to be updated
	 * @throws NotFoundException
	 *             no survivor found for the ID
	 */
	void update(Survivor survivor);

	/**
	 * gets the survivor by his id
	 * 
	 * @param survivorId
	 *            identifier of the survivor
	 * @return {@link Survivor} representing the survivor
	 */
	Survivor getSurvivor(String survivorId);

	/**
	 * find a survivor by name
	 * 
	 * @param name
	 *            name o the survivor
	 * @return survivor for the given name
	 */
	Survivor findOneByName(String name);
	
	/**
	 * gets the infected survivors
	 * @return the total
	 */
	List<Survivor> getInfectedSurvivors();
	
	/**
	 * gets the non infected survivors
	 * @return the total
	 */
	List<Survivor> getNonInfectedSurvivors();
	
}
