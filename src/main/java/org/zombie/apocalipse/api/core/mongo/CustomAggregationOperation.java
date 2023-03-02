package org.zombie.apocalipse.api.core.mongo;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import com.mongodb.DBObject;

/**
 * A custom representation of the Aggregation operation from Spring-data MongoDB
 * @author Itair Miguel
 *
 */
public class CustomAggregationOperation implements AggregationOperation {
	private DBObject operation;

	public CustomAggregationOperation(DBObject operation) {
		this.operation = operation;
	}

	@Override
	public DBObject toDBObject(AggregationOperationContext context) {
		return context.getMappedObject(operation);
	}
}
