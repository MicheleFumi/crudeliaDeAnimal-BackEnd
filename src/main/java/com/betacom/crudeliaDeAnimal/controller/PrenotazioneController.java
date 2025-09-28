package com.betacom.crudeliaDeAnimal.controller;

import com.betacom.crudeliaDeAnimal.dto.PrenotazioneDTO;
import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.PrenotazioneReq;
import com.betacom.crudeliaDeAnimal.requests.ProdottoReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.IPrenotazioneServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Log4j2
@RestController
@RequestMapping("rest/prenotazione")
public class PrenotazioneController {

    private IPrenotazioneServices IPreS;

    public PrenotazioneController(IPrenotazioneServices IPreS) {
        this.IPreS = IPreS;
    }

    @GetMapping("listAll")
    public ResponseList<PrenotazioneDTO> listAll(){
        ResponseList<PrenotazioneDTO> r = new ResponseList<>();

        try {
            r.setRc(true);
            r.setDati(IPreS.listAll());
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }

    @GetMapping("/findById")
    public ResponseObject<PrenotazioneDTO> findById(@RequestParam(required = true) Integer id) {
        ResponseObject<PrenotazioneDTO> r = new ResponseObject<PrenotazioneDTO>();
        try {
            r.setRc(true);
            r.setDati(IPreS.findById(id));
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }
  @GetMapping("/findByIdUtente")
  public ResponseList<PrenotazioneDTO> findByIdUtente(@RequestParam(required = true) Integer id) {
    ResponseList<PrenotazioneDTO> r = new ResponseList<PrenotazioneDTO>();
    try {
      r.setRc(true);
      r.setDati(IPreS.findByIdUtente(id));
    }catch (Exception e){
      r.setMsg(e.getMessage());
      r.setRc(false);
    }
    return r;
  }

    @PostMapping("/create")
    public ResponseBase create(@RequestBody(required = true) PrenotazioneReq req) {
        ResponseBase r = new ResponseBase();
        try {
            r.setRc(true);
            IPreS.create(req);
        }catch (CrudeliaException e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }

    @PostMapping("/delete")

    public ResponseBase delete(@RequestBody(required = true) PrenotazioneReq req) {
        ResponseBase r = new ResponseBase();
        log.debug("test dal controller" + req);
        try {
            r.setRc(true);
            IPreS.delete(req);
        }catch (CrudeliaException e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }

    @PostMapping("/update")
    public ResponseBase update(@RequestBody(required = true) PrenotazioneReq req) {
        ResponseBase r = new ResponseBase();

        try {
            r.setRc(true);
            IPreS.update(req);
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }
}
