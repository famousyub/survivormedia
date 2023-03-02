package org.zombie.apocalipse.api.report.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zombie.apocalipse.api.core.EnvelopeResponse;
import org.zombie.apocalipse.api.report.service.SurvivorReportsService;

@RestController
@RequestMapping("${api.services-prefix}/reports")
public class SurvivorReportsController {
	
	@Autowired
	private SurvivorReportsService service;
	
	@RequestMapping(method=RequestMethod.GET, value="/infected")
	public EnvelopeResponse getInfectedReport(HttpServletRequest request) {
		
		return new EnvelopeResponse(service.percentageOfInfectedSurvivorReport());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/not_infected")
	public EnvelopeResponse getNotInfectedReport(HttpServletRequest request) {
		
		return new EnvelopeResponse(service.percentageOfNonInfectedSurvivorReport());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/inventory")
	public EnvelopeResponse getAverageResource(HttpServletRequest request) {
		
		return new EnvelopeResponse(service.inventoryAverageAmountOfItemsPerSurvivorReport());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/points_lost")
	public EnvelopeResponse getPointsLost(HttpServletRequest request) {
		
		return new EnvelopeResponse(service.inventoryPointsLostReport());
	}
	
}
