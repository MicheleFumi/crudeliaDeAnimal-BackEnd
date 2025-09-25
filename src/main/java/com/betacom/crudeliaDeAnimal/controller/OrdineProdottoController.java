package com.betacom.crudeliaDeAnimal.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.crudeliaDeAnimal.dto.OrdineProdottoDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.OrdineProdottoReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.IOrdineProdottoServices;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/rest/ordineProdotto")
public class OrdineProdottoController {

	private IOrdineProdottoServices ordineProSer;

	public OrdineProdottoController(IOrdineProdottoServices ordineProSer) {
		super();
		this.ordineProSer = ordineProSer;
	}
	
	@PostMapping("/create")
	public ResponseBase create(@RequestBody (required = true) OrdineProdottoReq req) {
		log.debug("create :" + req);
		ResponseBase r = new ResponseBase();
		
		try {
			r.setRc(true);
			ordineProSer.create(req);
		} catch (CrudeliaException e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@PutMapping("/update")
	public ResponseBase update(@RequestBody (required = true) OrdineProdottoReq req) {
		log.debug("update :" + req);
		ResponseBase r = new ResponseBase();

		try {
			r.setRc(true);
			ordineProSer.update(req);
		} catch (CrudeliaException e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@PostMapping("/remove")
	public ResponseBase remove(@RequestBody (required = true) OrdineProdottoReq req) {
		log.debug("remove :" + req);
		ResponseBase r = new ResponseBase();
		
		try {
			OrdineProdottoDTO  ordProd =  ordineProSer.delete(req);
			r.setRc(true);
			r.setMsg(ordProd.getQuantita().toString());
		} catch (CrudeliaException e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}
	
	@GetMapping("/list")
	public ResponseList<OrdineProdottoDTO> listAll() {
		log.debug("list ");
		ResponseList<OrdineProdottoDTO> r = new ResponseList<OrdineProdottoDTO>();
		
		try{
			r.setDati(ordineProSer.listAll());
			r.setRc(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}
	
	@GetMapping("/findById")
	public ResponseObject<OrdineProdottoDTO> listById( Integer id) {
		log.debug("list:"+ id );
		ResponseObject<OrdineProdottoDTO> r = new ResponseObject<OrdineProdottoDTO>();
		
		try{
			r.setRc(true);
			r.setDati(ordineProSer.findById(id));
		} catch (Exception e) {
			log.error(e.getMessage());
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}
	
}
