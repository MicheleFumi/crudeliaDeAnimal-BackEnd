package com.betacom.crudeliaDeAnimal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.crudeliaDeAnimal.dto.CarrelloDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.CarrelloProdottoReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.ICarrelloProdottoServices;

import lombok.extern.log4j.Log4j2;

@CrossOrigin("*")
@Log4j2
@RestController
@RequestMapping("rest/carrelloProdotto")
public class CarrelloProdottoController {

	@Autowired
	private ICarrelloProdottoServices carProSer;

	@PostMapping("/insertProdotto")
	public ResponseBase createProdotto(@RequestParam Integer idUtente,

			@RequestBody CarrelloProdottoReq prodottoReq) {

		log.debug("Id utente :" + idUtente + prodottoReq.toString());

		ResponseBase r = new ResponseBase();
		// ResponseObject<CarrelloDTO> respObject = new ResponseObject<>();
		try {

			carProSer.createProdotto(idUtente, prodottoReq);
			r.setRc(true);
			r.setMsg("Prodotto gia creato con successo!");

		} catch (CrudeliaException e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}

	
	@PostMapping("/updateProdotto")
	public ResponseBase updateProdotto(@RequestParam Integer idUtente, @RequestBody CarrelloProdottoReq prodottoReq) {

		ResponseBase r = new ResponseBase();

		try {
			carProSer.updateProdotto(idUtente, prodottoReq);
			r.setRc(true);
            r.setMsg("Prodotto aggiornato con successo!");


		} catch (CrudeliaException e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}

	
	@PostMapping("/deleteProdotto")
	public ResponseBase deleteProdotto(@RequestParam Integer idUtente, @RequestParam Integer idProdotto) {

		ResponseBase r = new ResponseBase();

		try {
			r.setRc(true);
			carProSer.deleteProdotto(idUtente, idProdotto);
			r.setMsg("Prodotto gia cancellato con successo!");

		} catch (CrudeliaException e) {
			r.setRc(false);
			r.setMsg(e.getMessage());
		}
		return r;
	}

	/*
	 * @PostMapping("/insertProdotto") public ResponseObject<CarrelloDTO>
	 * createProdotto(@RequestParam Integer idUtente,
	 * 
	 * @RequestBody CarrelloProdottoReq prodottoReq) { log.debug("Id utente :"
	 * +idUtente + prodottoReq.toString());
	 * 
	 * ResponseObject<CarrelloDTO> r = new ResponseObject<>(); try { r.setRc(true);
	 * r.setDati(carProSer.createProdotto(idUtente, prodottoReq));
	 * 
	 * } catch (CrudeliaException e) { r.setRc(false); r.setMsg(e.getMessage()); }
	 * return r; }
	 */

	/*
	 * @PostMapping("/updateProdotto") public ResponseObject<CarrelloDTO>
	 * updateProdotto(@RequestParam Integer idUtente,
	 * 
	 * @RequestBody CarrelloProdottoReq prodottoReq) { ResponseObject<CarrelloDTO> r
	 * = new ResponseObject<>(); try { r.setRc(true);
	 * r.setDati(carProSer.updateProdotto(idUtente, prodottoReq)); } catch
	 * (CrudeliaException e) { r.setRc(false); r.setMsg(e.getMessage()); } return r;
	 * }
	 */

	/*
	 * @PostMapping("/deleteProdotto") public ResponseObject<CarrelloDTO>
	 * deleteProdotto(@RequestParam Integer idUtente,
	 * 
	 * @RequestParam Integer idProdotto) { ResponseObject<CarrelloDTO> r = new
	 * ResponseObject<>(); try { r.setRc(true);
	 * r.setDati(carProSer.deleteProdotto(idUtente, idProdotto)); } catch
	 * (CrudeliaException e) { r.setRc(false); r.setMsg(e.getMessage()); } return r;
	 * }
	 */

}
