package com.betacom.crudeliaDeAnimal.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.crudeliaDeAnimal.dto.AnimaleDTO;
import com.betacom.crudeliaDeAnimal.requests.AnimaleReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.IAnimaleServices;

import lombok.extern.log4j.Log4j2;

@CrossOrigin("*")
@Log4j2
@RestController
@RequestMapping("rest/animale")
public class AnimaleController {

	private IAnimaleServices aniSer;

	public AnimaleController(IAnimaleServices aniSer) {
		super();
		this.aniSer = aniSer;
	}

	 @GetMapping("listAll")
	 public  ResponseList<AnimaleDTO> listAll(){
	        ResponseList<AnimaleDTO> r = new ResponseList<>();

	        try {
	            r.setRc(true);
	            r.setDati(aniSer.listAll());
	        }catch (Exception e){
	            r.setMsg(e.getMessage());
	            r.setRc(false);
	        }
	        return r;
	    }

    @GetMapping("findByUserId")
    public ResponseList<AnimaleDTO> findByidUtente(@RequestParam(required = true) Integer id){
        ResponseList<AnimaleDTO> r = new ResponseList<>();

        try {
            r.setRc(true);
            r.setDati(aniSer.findByUserId(id));
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }


	    @GetMapping("/findById")
	    public ResponseObject<AnimaleDTO> findById(@RequestParam(required = true) Integer id) {
	        ResponseObject<AnimaleDTO> r = new ResponseObject<AnimaleDTO>();
	        try {
	            r.setRc(true);
	            r.setDati(aniSer.findById(id));
	        }catch (Exception e){
	            r.setMsg(e.getMessage());
	            r.setRc(false);
	        }
	        return r;
	    }

	    @PostMapping("/create")
	    public ResponseBase create(@RequestBody(required = true) AnimaleReq req) {
	        ResponseBase r = new ResponseBase();
	        try {
	            r.setRc(true);
	            aniSer.create(req);
	            r.setMsg("Animale gia creato con successo!");    
	            
	        }catch (Exception e){
	            r.setMsg(e.getMessage());
	            r.setRc(false);
	        }
	        return r;
	    }

	    @PostMapping("/delete")

	    public ResponseBase delete(@RequestBody(required = true) AnimaleReq req) {
	        ResponseBase r = new ResponseBase();

	        try {
	            r.setRc(true);
	            AnimaleDTO p = aniSer.delete(req);
	        }catch (Exception e){
	            r.setMsg(e.getMessage());
	            r.setRc(false);
	        }
	        return r;
	    }

	    @PostMapping("/update")
	    public ResponseBase update(@RequestBody(required = true) AnimaleReq req) {
	        ResponseBase r = new ResponseBase();

	        try {
	            r.setRc(true);
	            aniSer.update(req);
	            r.setMsg("Animale aggiornato con successo!");
	        }catch (Exception e){
	            r.setMsg(e.getMessage());
	            r.setRc(false);
	        }
	        return r;
	    }
}
