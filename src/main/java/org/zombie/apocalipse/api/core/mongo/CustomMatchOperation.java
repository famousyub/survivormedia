package org.zombie.apocalipse.api.core.mongo;

import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * generates a custom match with an array of criterias
 * 
 * @author itair.miguel
 *
 */
public class CustomMatchOperation extends MatchOperation {

	public CustomMatchOperation(CriteriaDefinition criteriaDefinition) {
		super(criteriaDefinition);
	}

	private Criteria[] criterias;

	/**
	 * Builder for custom match operation
	 * 
	 * @return new MatchOperation
	 */
	public static CustomMatchOperation set() {
		return new CustomMatchOperation(null);
	}

	/**
	 * returns the object with a list of criterias
	 * 
	 * @param criterias
	 *            - criterias to create the DBObject
	 * @return CustomMatchOperation this implemented class
	 */
	public CustomMatchOperation match(Criteria... criterias) {
		Assert.notNull(criterias, "var args may not be empty.");
		this.criterias = criterias;
		return this;
	}

	@Override
	public DBObject toDBObject(AggregationOperationContext context) {
		DBObject dbo = new BasicDBObject();
		for (CriteriaDefinition definition : criterias) {
			dbo.putAll(definition.getCriteriaObject());
		}
		return new BasicDBObject("$match", dbo);
	}
}
