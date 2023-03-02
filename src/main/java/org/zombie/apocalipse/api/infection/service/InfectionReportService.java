package org.zombie.apocalipse.api.infection.service;

import org.bson.types.ObjectId;
import org.zombie.apocalipse.api.core.service.CrudService;
import org.zombie.apocalipse.api.infection.dto.InfectionReportDTO;
import org.zombie.apocalipse.api.infection.model.InfectionReport;

public interface InfectionReportService extends CrudService<InfectionReport, InfectionReportDTO> {

	/**
	 * validates if the given reporter have already reported the given infected
	 * survivor
	 * 
	 * @param reporter
	 *            ID of the one reporting
	 * @param infectedSurvivor
	 *            ID of the one being reported
	 * @return true if the reporter have already reported the infected survivor
	 */
	boolean haveAlreadyReportedThisSurvivor(ObjectId reporter, ObjectId infectedSurvivor);
}
