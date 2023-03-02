package org.zombie.apocalipse.api.mongo;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.zombie.apocalipse.api.core.mongo.QueryBuilder;

public class QueryBuilderTest {

	@Test
	public void shouldCreateIsCriteria() {
		Query espected = new Query();
		espected.addCriteria(Criteria.where("any").is("any"));

		Query query = new QueryBuilder().is("any", "any").build();

		assertThat(query, equalTo(espected));
	}

	@Test
	public void shouldCreateInCriteria() {
		Query espected = new Query();
		espected.addCriteria(Criteria.where("any").in(new ArrayList<>()));

		Query query = new QueryBuilder().in("any", new ArrayList<>()).build();

		assertThat(query, equalTo(espected));
	}

	@Test
	public void shouldCreateQueryWithManyCriterias() {
		Query espected = new Query();
		espected.addCriteria(Criteria.where("any").is("any"));
		espected.addCriteria(Criteria.where("any2").in( new ArrayList<>()));
		espected.addCriteria(Criteria.where("any3").is(4));
		espected.addCriteria(Criteria.where("any4").in( new ArrayList<>()));

		Query query = new QueryBuilder().is("any", "any").in("any2", new ArrayList<>()).is("any3", 4).in("any4",  new ArrayList<>()).build();

		assertThat(query, equalTo(espected));
	}
	
	@Test
	public void shouldNotCreateQueryWhenNullValueIsPassed() {
		Query espected = null;
		
		Query query = new QueryBuilder().is("any", null).build();

		assertThat(query, equalTo(espected));
	}

}
