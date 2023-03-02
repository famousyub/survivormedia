package org.zombie.apocalipse.api.infection.dto;

import java.util.Date;

import org.bson.types.ObjectId;
import org.zombie.apocalipse.api.core.dto.AbstractDocumentDTO;

public class InfectionReportDTO extends AbstractDocumentDTO {

	private ObjectId reported;
	private Date dTEvent;
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

	public ObjectId getReporter() {
		return reporter;
	}

	public void setReporter(ObjectId reporter) {
		this.reporter = reporter;
	}

}
