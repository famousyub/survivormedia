package org.zombie.apocalipse.api.core.repository;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.zombie.apocalipse.api.core.model.Model;

public interface CustomRepository<E extends Model> {

	/**
	 * updates the collection
	 * @param query query the documents to update
	 * @param update update to be done
	 * @return true if everything went ok 
	 */
	boolean update(Query query, Update update);
	
	/**
	 * find all documents by the given query
	 * @return
	 */
	List<E> findByQuery(Query query);
}
