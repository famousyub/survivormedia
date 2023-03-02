package org.zombie.apocalipse.api.infection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.zombie.apocalipse.api.infection.dto.InfectionReportDTO;
import org.zombie.apocalipse.api.infection.model.InfectionReport;
import org.zombie.apocalipse.api.infection.service.InfectionReportMapper;

public class InfectionReportMapperTest {

	private InfectionReportMapper mapper;
	
	@Before
	public void setUp() {
		ModelMapper model = new ModelMapper();
		this.mapper = new InfectionReportMapper(model);
	}
	
	@Test
	public void shouldMapDtoToEntity() {
		InfectionReportDTO dto = new InfectionReportDTO();
		dto.setdTEvent(new Date());
		dto.setReported(new ObjectId("58ea61542b43926ff026bafa"));
		dto.setReporter(new ObjectId("58ea618d2b4392482c59efeb"));
		
		InfectionReport report = mapper.toEntity(dto);
		assertThat(report.getdTEvent(), equalTo(dto.getdTEvent()));
		assertThat(report.getReported(), equalTo(dto.getReported()));
		assertThat(report.getReporter(), equalTo(dto.getReporter()));
	}
	
	@Test
	public void souldCreateTimeBucket() {
		Date dtEvent = new Date(1491765431000L);//Sun, 09 Apr 2017 16:17:11
		List<String> timeBucket = new ArrayList<>();
		timeBucket.add("2017-04-09 16-hour");
		timeBucket.add("2017-04-09-day");
		timeBucket.add("2017-04-03-month_week");
		timeBucket.add("2017-04-month");
		timeBucket.add("2017-15-year_week");
		timeBucket.add("2017-year");
		
		String[] bucket = mapper.createTimeBucket(dtEvent);
		
		assertThat(bucket[0], equalTo(timeBucket.get(0)));
		assertThat(bucket[1], equalTo(timeBucket.get(1)));
		assertThat(bucket[2], equalTo(timeBucket.get(2)));
		assertThat(bucket[3], equalTo(timeBucket.get(3)));
		assertThat(bucket[4], equalTo(timeBucket.get(4)));
		assertThat(bucket[5], equalTo(timeBucket.get(5)));
	}

}
