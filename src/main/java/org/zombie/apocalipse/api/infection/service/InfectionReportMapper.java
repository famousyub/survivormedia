package org.zombie.apocalipse.api.infection.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.zombie.apocalipse.api.core.mapper.AbstractGenericMapper;
import org.zombie.apocalipse.api.infection.dto.InfectionReportDTO;
import org.zombie.apocalipse.api.infection.model.InfectionReport;

@Component
public class InfectionReportMapper extends AbstractGenericMapper<InfectionReport, InfectionReportDTO> {

	public InfectionReportMapper(ModelMapper mapper) {
		super(mapper, InfectionReport.class, InfectionReportDTO.class);
	}

	@Override
	public InfectionReport toEntity(InfectionReportDTO dto) {
		InfectionReport report = super.toEntity(dto);
		report.setTimeBucket(this.createTimeBucket(dto.getdTEvent()));
		return report;
	}

	/**
	 * creates a time bucket for the given date
	 * 
	 * @param dtEvent
	 *            date of the event to bucket
	 * @return array of strings
	 */
	public String[] createTimeBucket(Date dtEvent) {
		String[] formats = DateTimeBucketFormat.getAllFormats();
		String[] representation = DateTimeBucketFormat.getAllRepresentations();
		String[] timeBucket = new String[formats.length];
		for (int i = 0; i < formats.length; i++) {
			timeBucket[i] = (new SimpleDateFormat(formats[i]).format(dtEvent) + representation[i]);
		}

		return timeBucket;
	}

}
