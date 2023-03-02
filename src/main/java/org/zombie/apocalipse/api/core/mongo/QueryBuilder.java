package org.zombie.apocalipse.api.core.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class QueryBuilder {
	
	private Query query;
	
	public Query build() {
		return this.query;
	}
	
	public QueryBuilder or(Criteria... criterias) {
		if(criterias != null) {
			this.checkQuery();
			this.query.addCriteria(new Criteria().orOperator(criterias));
		}
		return this;
	}
	
	public QueryBuilder is(String key, Object value) {
		if(value != null) {
			this.checkQuery();
			query.addCriteria(Criteria.where(key).is(value));
		}
		return this;
	}
	
	public QueryBuilder in(String key, List<?> values) {
		if(values != null) {
			this.checkQuery();
			query.addCriteria(Criteria.where(key).in(values));
		}
		return this;
	}
	
	public QueryBuilder gte(String key, Object value) {
		if(value != null) {
			this.checkQuery();
			query.addCriteria(Criteria.where(key).gte(value));
		}
		return this;
	}
	
	public QueryBuilder lte(String key, Object value) {
		if(value != null) {
			this.checkQuery();
			query.addCriteria(Criteria.where(key).lte(value));
		}
		return this;
	}
	
	private void checkQuery() {
		if(query == null) {
			query = new Query();
		}
	}
	
}
