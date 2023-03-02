package org.zombie.apocalipse.api.infection.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.zombie.apocalipse.api.infection.model.InfectionReport;

public interface InfectionReportRepository extends MongoRepository<InfectionReport, String> {

	/**
	 * find an {@link InfectionReport} that matches the criteria
	 * 
	 * @param reporter
	 *            the reporter
	 * @param reported
	 *            the infected survivor
	 * @return the report
	 */
	InfectionReport findByReporterAndReported(ObjectId reporter, ObjectId reported);

}
