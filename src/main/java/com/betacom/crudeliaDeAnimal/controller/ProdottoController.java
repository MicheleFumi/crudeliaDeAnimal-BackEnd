package com.betacom.crudeliaDeAnimal.controller;

import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.requests.ProdottoReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.IProdottoServices;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Log4j2
@RestController
@RequestMapping("rest/prodotto")
public class ProdottoController {

	private IProdottoServices IProS;
	private IUtenteRepository utenteRepo;

	   public ProdottoController(IProdottoServices IProS, IUtenteRepository utenteRepo) {
	        this.IProS = IProS;
	        this.utenteRepo = utenteRepo;
	    }
	   
	@GetMapping("listAll")
	public ResponseList<ProdottoDTO> listAll() {
		ResponseList<ProdottoDTO> r = new ResponseList<>();

		try {
			r.setRc(true);
			r.setDati(IProS.listAll());
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@GetMapping("/findById")

	public ResponseObject<ProdottoDTO> findById(@RequestParam(required = true) Integer id) {
		
		ResponseObject<ProdottoDTO> r = new ResponseObject<ProdottoDTO>();
		try {
			r.setRc(true);
			r.setDati(IProS.findById(id));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@PostMapping("/create")

	public ResponseBase create(@RequestBody(required = true) ProdottoReq req) {
		ResponseBase r = new ResponseBase();
		try {
			r.setRc(true);

			Optional<Utente> userOp = utenteRepo.findById(req.getUserId());

			Utente user = userOp.get();

			IProS.create(user, req);

		} catch (Exception e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@PostMapping("/delete")

	public ResponseBase delete(@RequestBody(required = true) ProdottoReq req) {
		ResponseBase r = new ResponseBase();

		try {
			
			Optional<Utente> userOp = utenteRepo.findById(req.getUserId());

			Utente user = userOp.get();
			r.setRc(true);
			IProS.delete(user, req);
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}

	@PostMapping("/update")
	public ResponseBase update(@RequestBody(required = true) ProdottoReq req) {
		ResponseBase r = new ResponseBase();

		try {
			Optional<Utente> userOp = utenteRepo.findById(req.getUserId());

			Utente user = userOp.get();
			r.setRc(true);
			IProS.update(user, req);
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}
}
