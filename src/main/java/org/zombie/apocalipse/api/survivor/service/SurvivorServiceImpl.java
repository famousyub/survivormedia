package org.zombie.apocalipse.api.survivor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zombie.apocalipse.api.core.exception.InvalidParamsException;
import org.zombie.apocalipse.api.core.exception.NotFoundException;
import org.zombie.apocalipse.api.core.exception.ServiceException;
import org.zombie.apocalipse.api.core.service.AbstractCrudService;
import org.zombie.apocalipse.api.infection.service.InfectionReportService;
import org.zombie.apocalipse.api.invetory.service.InventoryService;
import org.zombie.apocalipse.api.survivor.dto.SurvivorDTO;
import org.zombie.apocalipse.api.survivor.dto.TradeDTO;
import org.zombie.apocalipse.api.survivor.model.Survivor;
import org.zombie.apocalipse.api.survivor.repository.SurvivorRepository;

/**
 * implementation of {@link SurvivorService}. Heavy lifting is done here!
 * 
 * @author Itair Miguel
 *
 */
@Service
public class SurvivorServiceImpl extends AbstractCrudService<Survivor, SurvivorDTO, SurvivorRepository, SurvivorMapper>
		implements SurvivorService {

	private static final Logger LOGGER = LogManager.getLogger(SurvivorServiceImpl.class);

	private SurvivorRepository repo;
	private InventoryService inventory;
	private InfectionReportService report;
	private SurvivorMapper mapper;

	@Autowired
	public SurvivorServiceImpl(SurvivorRepository repo, InventoryService inventory, InfectionReportService report,
			SurvivorMapper mapper) {
		super(repo, mapper);
		this.repo = repo;
		this.inventory = inventory;
		this.report = report;
		this.mapper = mapper;
	}

	@Override
	public List<SurvivorDTO> searchAll() {
		return mapper.toDtoList(repo.getAllSurvivors());
	}

	@Override
	public SurvivorDTO search(String id) {
		return mapper.toDto(repo.getSurvivor(id));
	}

	@Override
	public SurvivorDTO create(SurvivorDTO dto) {
		Survivor survivor;

		if (dto.getLastLocation().length < 2) {
			throw new InvalidParamsException(
					"Having a correct location is always necessary. Please send latitude and longitude.");
		}

		if ((survivor = repo.save(mapper.toEntity(dto))) == null) {
			throw new ServiceException("Could not create a new survivor record.");
		}

		dto.setId(survivor.getId());

		try {
			inventory.create(mapper.toInventoryDto(dto));
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage(), e);
			repo.delete(survivor.getId().toString());
			throw new ServiceException(e.getMessage(), e);
		}

		return dto;
	}

	@Override
	public void update(SurvivorDTO dto) {
		repo.update(mapper.toEntity(dto));
	}

	@Override
	public void updateInfectionCounter(String reporterId, String infectedName) {
		SurvivorDTO infected = new SurvivorDTO();
		infected.setIncrementReports(true);

		SurvivorServiceUtils.validateIds(reporterId);

		Survivor infectedSuspect;
		if((infectedSuspect = repo.findOneByName(infectedName)) == null || infectedSuspect.getId() == null) {
			throw new InvalidParamsException("The given survivor name could not be found: " + infectedName);
		}
		infected.setId(infectedSuspect.getId());

		if (infected.getId().equals(reporterId)) {
			throw new InvalidParamsException("Can't report and be reported as infected.");
		}

		if (report.haveAlreadyReportedThisSurvivor(new ObjectId(reporterId), new ObjectId(infected.getId()))) {
			throw new InvalidParamsException("You have already reported this survivor.");
		}

		try {

			SurvivorDTO reporter = this.search(reporterId);
			repo.update(mapper.toEntity(infected));
			report.create(mapper.toInfectionReportDTO(new ObjectId(infected.getId()), new ObjectId(reporter.getId())));

		} catch (NotFoundException e) {
			throw new InvalidParamsException("The given IDs could not be found: " + reporterId, e);
		} catch (ServiceException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void trade(String sellerId, TradeDTO trade) {
		SurvivorServiceUtils.validateIds(sellerId, trade.getBuyerId());
		
		if(sellerId.equals(trade.getBuyerId())) {
			throw new InvalidParamsException("Cannot perform trade between the same survivor.");
		}

		try {
			SurvivorDTO seller = this.search(sellerId);
			SurvivorDTO buyer = this.search(trade.getBuyerId());

			if (seller.getContaminationReports() >= 3 || buyer.getContaminationReports() >= 3) {
				throw new InvalidParamsException("Careful now! do not trade with a zombie.");
			}

			SurvivorServiceUtils.verifyTrade(trade, seller, buyer);

			SurvivorServiceUtils.doPerformTrade(trade, seller, buyer);

			inventory.update(mapper.toInventoryDto(seller));
			inventory.update(mapper.toInventoryDto(buyer));

		} catch (NotFoundException e) {
			throw new NotFoundException(
					"One of the given IDs could not be found. Currently: " + sellerId + " / " + trade.getBuyerId(), e);
		}

	}

	@Override
	public Integer totalInfectedSurvivors() {
		List<Survivor> survivors = repo.getInfectedSurvivors();
		if(survivors != null && survivors.size() > 0) {
			return survivors.size();
		}
		return 0;
	}

	@Override
	public Integer totalNonlInfectedSurvivors() {
		List<Survivor> survivors = repo.getNonInfectedSurvivors();
		if(survivors != null && survivors.size() > 0) {
			return survivors.size();
		}
		return 0;
	}

	@Override
	public List<String> getInfectedSurvivorsId() {
		List<Survivor> survivors = repo.getInfectedSurvivors();
		if(survivors != null && survivors.size() > 0) {
			return survivors.stream().map(e -> e.getId()).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<String> getNonInfectedSurvivorsId() {
		List<Survivor> survivors = repo.getNonInfectedSurvivors();
		if(survivors != null && survivors.size() > 0) {
			return survivors.stream().map(e -> e.getId()).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public Long getTotalSurvivors() {
		return repo.count();
	}
	
}
