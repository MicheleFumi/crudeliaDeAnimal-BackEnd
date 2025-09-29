package com.betacom.crudeliaDeAnimal.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.crudeliaDeAnimal.dto.CarrelloDTO;
import com.betacom.crudeliaDeAnimal.dto.CarrelloProdottoDTO;
import com.betacom.crudeliaDeAnimal.dto.CarrelloRespDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.ICarrelloServices;

import lombok.extern.log4j.Log4j2;

@CrossOrigin("*")
@Log4j2
@RestController
@RequestMapping("rest/carrello")
public class CarrelloController {
	
	 private  ICarrelloServices carrelloSer;
	 

	 public CarrelloController(ICarrelloServices carrelloSer) {
		super();
		this.carrelloSer = carrelloSer;
	 }
	 
	 @GetMapping("/getCarrello")
	    public ResponseObject<CarrelloRespDTO> getCarrello(@RequestParam Integer idUtente) {
		 
	        ResponseObject<CarrelloRespDTO> resp = new ResponseObject<CarrelloRespDTO>();
		 
	        try {
	        	resp.setRc(true);
	            resp.setDati(carrelloSer.getCarrelloByUtente(idUtente , true));
	            resp.setMsg("Carrello gia creato con successo!");
	        } catch (CrudeliaException e) {
	        	log.debug("get carrello errore "+ e.getMessage());
	        	resp.setRc(false);
	            resp.setMsg(e.getMessage());
	            
	        }
	        return resp;
	    }
	 
	
	
	 @PostMapping("/emptyCarrello")
	    public ResponseBase emptyCarrello(@RequestParam Integer idUtente) {
	        ResponseBase r = new ResponseBase();
	        try {
	            r.setRc(true);
	            carrelloSer.emptyCarrello(idUtente);
	            r.setMsg("Carrello svuotato con successo!");
	        } catch (CrudeliaException e) {
	            r.setRc(false);
	            r.setMsg(e.getMessage());
	        }
	        return r;
	    }
	 
		/*
		 * @GetMapping("/get") public ResponseObject<CarrelloDTO>
		 * getCarrello(@RequestParam Integer idUtente) { ResponseObject<CarrelloDTO> r =
		 * new ResponseObject<>(); try { r.setRc(true);
		 * r.setDati(carrelloSer.getCarrelloByUtente(idUtente , true)); } catch
		 * (CrudeliaException e) { r.setRc(false); r.setMsg(e.getMessage()); } return r;
		 * }
		 */
}