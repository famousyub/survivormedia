package org.zombie.apocalipse.api.infection.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zombie.apocalipse.api.core.service.AbstractCrudService;
import org.zombie.apocalipse.api.infection.dto.InfectionReportDTO;
import org.zombie.apocalipse.api.infection.model.InfectionReport;
import org.zombie.apocalipse.api.infection.repository.InfectionReportRepository;

@Service
public class InfectionReportServiceImpl extends AbstractCrudService<InfectionReport, InfectionReportDTO, InfectionReportRepository, InfectionReportMapper> implements InfectionReportService {
	
	@Autowired
	private InfectionReportRepository repo;
	
	public InfectionReportServiceImpl(InfectionReportRepository repository, InfectionReportMapper mapper) {
		super(repository, mapper);
	}

	@Override
	public boolean haveAlreadyReportedThisSurvivor(ObjectId reporter, ObjectId infectedSurvivor) {
		return repo.findByReporterAndReported(reporter, infectedSurvivor) != null;
	}
	
}
