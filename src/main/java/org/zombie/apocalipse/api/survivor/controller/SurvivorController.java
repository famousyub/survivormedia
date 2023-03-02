package org.zombie.apocalipse.api.survivor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zombie.apocalipse.api.core.EnvelopeResponse;
import org.zombie.apocalipse.api.core.MessageResponse;
import org.zombie.apocalipse.api.survivor.dto.SurvivorDTO;
import org.zombie.apocalipse.api.survivor.dto.TradeDTO;
import org.zombie.apocalipse.api.survivor.service.SurvivorService;

/**
 * primary controller that services data of the survivors
 * @author Itair Miguel
 *
 */
@RestController
@RequestMapping("${api.services-prefix}/survivors")
public class SurvivorController {
	
	@Autowired
	private SurvivorService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public EnvelopeResponse listAll(HttpServletRequest request) {
		
		return new EnvelopeResponse(service.searchAll());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public EnvelopeResponse get(HttpServletRequest request, @PathVariable String id) throws Exception {

		return new EnvelopeResponse(service.search(id));
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EnvelopeResponse> create(@Valid @RequestBody SurvivorDTO dto, 
			HttpServletRequest request) throws Exception {
		
		dto.setId(null);
		
		return this.getWrappedCreationResponse(service.create(dto));
	}
	
	@RequestMapping(method=RequestMethod.PATCH, value="/{id}", consumes={MediaType.APPLICATION_JSON_VALUE})
	public MessageResponse update(@PathVariable String id, @RequestBody SurvivorDTO dto, 
			HttpServletRequest request) throws Exception {
		
		dto.setId(id);
		service.update(dto);
		
		return MessageResponse.build("Survivor updated.");
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/{id}/report")
	public MessageResponse reportInfected(@PathVariable String id, @RequestParam(required=true) String infectedName, 
			HttpServletRequest request) throws Exception {
		
		service.updateInfectionCounter(id, infectedName);
		
		return MessageResponse.build("Infected survivor succesfully reported.");
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/{id}/trade", consumes={MediaType.APPLICATION_JSON_VALUE})
	public MessageResponse trade(@PathVariable String id, @RequestBody @Valid TradeDTO dto, 
			HttpServletRequest request) throws Exception {
		
		service.trade(id, dto);
		
		return MessageResponse.build("Trade succesful.");
	}
	
	/**
     * sets the response in case of a new created object
     * @param createdDto - recently created object and its id
     * @return ResponseEntity creation response
     */
	private ResponseEntity<EnvelopeResponse> getWrappedCreationResponse(SurvivorDTO createdDto) {
		EnvelopeResponse envResponse = new EnvelopeResponse(createdDto);
		HttpHeaders headers = getHeaderWithLocation((String) createdDto.getId());
		return new ResponseEntity<EnvelopeResponse>(envResponse, headers, HttpStatus.CREATED);
	}
	
	/**
     * sets the header of the HTTP
     * @param id - ObjectId created by mongoDB
     * @return HttpHeaders header of the response
     */
    private HttpHeaders getHeaderWithLocation(String id) {
		HttpHeaders headers = new HttpHeaders();
		ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return headers;
	}
	
}
