package org.zombie.apocalipse.api.survivor.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.zombie.apocalipse.api.core.exception.InvalidParamsException;
import org.zombie.apocalipse.api.core.exception.NotFoundException;
import org.zombie.apocalipse.api.core.mongo.QueryBuilder;
import org.zombie.apocalipse.api.core.mongo.UpdateBuilder;
import org.zombie.apocalipse.api.core.repository.AbstractCustomRepository;
import org.zombie.apocalipse.api.survivor.model.Survivor;

/**
 * implements the survivor repository by convention
 * 
 * @author Itair Miguel
 *
 */
@Repository
public class SurvivorRepositoryImpl extends AbstractCustomRepository<Survivor> {

	private static final String COLLECTION = "survivors";
	private static final String LOOKUP_FROM = "inventories";
	private static final String LOOKUP_LOCAL = "_id";
	private static final String LOOKUP_FOREIGN = "survivorId";
	private static final String LOOKUP_AS = "inventory";

	@Autowired
	private MongoTemplate temp;

	@Autowired
	public SurvivorRepositoryImpl(MongoTemplate temp) {
		super(temp, Survivor.class);
	}

	/**
	 * list all the survivors and their inventory
	 * 
	 * @return list of survivors
	 */
	public List<Survivor> getAllSurvivors() {
		Aggregation agg = Aggregation
				.newAggregation(Aggregation.lookup(LOOKUP_FROM, LOOKUP_LOCAL, LOOKUP_FOREIGN, LOOKUP_AS));

		return this.aggregate(agg);
	}

	/**
	 * gets the survivor by his id
	 * 
	 * @param survivorId
	 *            identifier of the survivor
	 * @return {@link Survivor} representing the survivor
	 */
	public Survivor getSurvivor(String survivorId) {
		if (!ObjectId.isValid(survivorId)) {
			throw new InvalidParamsException("The given ID is not valid. Currently: " + survivorId);
		}
		Aggregation agg = Aggregation.newAggregation(
				new MatchOperation(Criteria.where("_id").is(new ObjectId(survivorId))),
				Aggregation.lookup(LOOKUP_FROM, LOOKUP_LOCAL, LOOKUP_FOREIGN, LOOKUP_AS));

		return this.aggregate(agg).get(0);
	}

	/**
	 * returns the aggregation result
	 * 
	 * @param agg
	 *            aggregation to be done by mongodb
	 * @return a list of survivors
	 * @throws NotFoundException
	 *             if no survivor has been found
	 */
	private List<Survivor> aggregate(Aggregation agg) {
		AggregationResults<Survivor> results = temp.aggregate(agg, COLLECTION, Survivor.class);
		List<Survivor> mappedResults = results.getMappedResults();
		if (mappedResults == null || mappedResults.isEmpty()) {
			throw new NotFoundException("Don't Panic. No survivors were found.");
		}
		return mappedResults;
	}

	/**
	 * updates the survivor's location
	 * 
	 * @param survivor
	 *            survivor to be updated
	 * @throws NotFoundException
	 *             no survivor found for the ID
	 */
	public void update(Survivor survivor) {
		Query query = new QueryBuilder().is("_id", survivor.getId()).build();

		Update update = new UpdateBuilder()
				.set("age", survivor.getAge())
				.set("name", survivor.getName())
				.set("gender", survivor.getGender())
				.set("position", survivor.getPosition())
				.inc("contaminationReports", survivor.isIncrementReports()).build();

		if (update == null) {
			throw new InvalidParamsException("Nothing to update.");
		}

		boolean isOk = super.update(query, update);

		if (!isOk) {
			throw new NotFoundException(
					"Not possible to update the survivor. No survivor was found for the given ID: " + survivor.getId());
		}
	}

	public List<Survivor> getInfectedSurvivors() {
		return temp.find(new QueryBuilder().gte("contaminationReports", 3).build(), Survivor.class);
	}

	public List<Survivor> getNonInfectedSurvivors() {
		return temp.find(new QueryBuilder()
				.or(Criteria.where("contaminationReports").lt(3), 
						Criteria.where("contaminationReports").exists(false)).build(), Survivor.class);
	}

}
