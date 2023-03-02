package org.zombie.apocalipse.api.core.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.zombie.apocalipse.api.core.model.Model;

import com.mongodb.WriteResult;

public abstract class AbstractCustomRepository<E extends Model> implements CustomRepository<E> {

	private final MongoTemplate template;

	private Class<E> entity;

	@Autowired
	public AbstractCustomRepository(MongoTemplate temp, Class<E> entity) {
		this.template = temp;
		this.entity = entity;
	}

	@Override
	public boolean update(Query query, Update update) {
		WriteResult result = template.updateFirst(query, update, entity);
		return result.isUpdateOfExisting();
	}
	
	@Override
	public List<E> findByQuery(Query query) {
		return template.find(query, entity);
	}

}
