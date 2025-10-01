package com.betacom.crudeliaDeAnimal.controller;

import com.betacom.crudeliaDeAnimal.dto.SlotDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.betacom.crudeliaDeAnimal.dto.VeterinarioDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.VeterinarioReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.IVeterinarioServices;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/rest/veterinario")
public class VeterinarioController {

	private IVeterinarioServices  vetSer;

	public VeterinarioController(IVeterinarioServices vetSer) {
		super();
		this.vetSer = vetSer;
	}

	@PostMapping("/create")
	public ResponseBase create(@RequestBody (required = true) VeterinarioReq req) {
		log.debug("create :" + req);
		ResponseBase r = new ResponseBase();
		r.setRc(true);
		try {
			vetSer.create(req);
		} catch (CrudeliaException e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@PutMapping("/update")
	public ResponseBase update(@RequestBody (required = true) VeterinarioReq req) {
		log.debug("update :" + req);
		ResponseBase r = new ResponseBase();

		try {
            r.setRc(true);
			vetSer.update(req);
		} catch (CrudeliaException e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@PostMapping("/remove")
	public ResponseBase remove(@RequestBody (required = true) VeterinarioReq req) {
		log.debug("remove :" + req);
		ResponseBase r = new ResponseBase();

		try {
			VeterinarioDTO veter =  vetSer.delete(req);
			r.setRc(true);
			r.setMsg(veter.getNome());
		} catch (CrudeliaException e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}

	@GetMapping("/list")
	public ResponseList<VeterinarioDTO> listAll() {
		log.debug("list ");
		ResponseList<VeterinarioDTO> r = new ResponseList<VeterinarioDTO>();
		r.setRc(true);
		try{
			r.setDati(vetSer.listAll());
		} catch (Exception e) {
			log.error(e.getMessage());
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@GetMapping("/findById")
	public ResponseObject<VeterinarioDTO> listById( Integer id) {
		log.debug("list:"+ id );
		ResponseObject<VeterinarioDTO> r = new ResponseObject<VeterinarioDTO>();

		try{
            r.setRc(true);
			r.setDati(vetSer.findById(id));
		} catch (CrudeliaException e) {
			log.error(e.getMessage());
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}
  @GetMapping("/findByIdUtente")
  public ResponseList<VeterinarioDTO> findByIdUtente(@RequestParam Integer idUtente){
    ResponseList<VeterinarioDTO> r = new ResponseList<>();
    try {
      r.setDati(vetSer.findByIdUtente(idUtente));
      r.setRc(true);
    } catch (CrudeliaException e) {
      r.setMsg(e.getMessage());
      r.setRc(false);
    }
    return r;
  }

	@GetMapping("/findByCap")
	public ResponseList<VeterinarioDTO> listByCap( String cap) {

		log.debug("list:"+ cap );

		ResponseList<VeterinarioDTO> r = new ResponseList<VeterinarioDTO>();


		try{
			r.setDati(vetSer.findByCap(cap));
            r.setRc(true);
		} catch (CrudeliaException e) {
			log.error(e.getMessage());
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@GetMapping("/findByProvincia")
	public ResponseList<VeterinarioDTO> listByProvincia( String provincia) {
		log.debug("list:"+ provincia );
		ResponseList<VeterinarioDTO> r = new ResponseList<VeterinarioDTO>();

		try{
            r.setRc(true);
			r.setDati(vetSer.findByProvincia(provincia));
		} catch (CrudeliaException e) {
			log.error(e.getMessage());
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@GetMapping("/findByRegione")
	public ResponseList<VeterinarioDTO> listByRegione( String regione) {
		log.debug("list:"+ regione );
		ResponseList<VeterinarioDTO> r = new ResponseList<VeterinarioDTO>();

		try{
            r.setRc(true);
			r.setDati(vetSer.findByRegione(regione));
		} catch (CrudeliaException e) {
			log.error(e.getMessage());
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}


}
