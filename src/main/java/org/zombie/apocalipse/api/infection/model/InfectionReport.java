package org.zombie.apocalipse.api.infection.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.zombie.apocalipse.api.core.model.AbstractModel;

@Document(collection = "infectionReports")
public class InfectionReport extends AbstractModel {

	private ObjectId reported;
	private Date dTEvent;
	private String[] timeBucket;
	private ObjectId reporter;

	public ObjectId getReported() {
		return reported;
	}

	public void setReported(ObjectId reported) {
		this.reported = reported;
	}

	public Date getdTEvent() {
		return dTEvent;
	}

	public void setdTEvent(Date dTEvent) {
		this.dTEvent = dTEvent;
	}

	public String[] getTimeBucket() {
		return timeBucket;
	}

	public void setTimeBucket(String[] timeBucket) {
		this.timeBucket = timeBucket;
	}

	public ObjectId getReporter() {
		return reporter;
	}

	public void setReporter(ObjectId reporter) {
		this.reporter = reporter;
	}

}
