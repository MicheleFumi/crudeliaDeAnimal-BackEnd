package com.betacom.crudeliaDeAnimal.controller;

import com.betacom.crudeliaDeAnimal.dto.SignInDTO;
import com.betacom.crudeliaDeAnimal.dto.UtenteDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.SignInReq;
import com.betacom.crudeliaDeAnimal.requests.UtenteReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.services.interfaces.IUtenteServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@Log4j2
@RestController
@RequestMapping("rest/utente")
public class UtenteController {

    private IUtenteServices iUtenteServices;

    public UtenteController(IUtenteServices iUtenteServices) {
        this.iUtenteServices = iUtenteServices;
    }

    @GetMapping("listAll")
   public ResponseList<UtenteDTO> listAll(){
        ResponseList<UtenteDTO> r = new ResponseList<>();
        r.setRc(true);
        try {
            r.setDati(iUtenteServices.listAll());
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }

    @PostMapping("/signin")
    public SignInDTO signin(@RequestBody(required = true)SignInReq req) throws CrudeliaException {
        return iUtenteServices.signIn(req);
    };

    @PostMapping("/create")
    public ResponseBase create(@RequestBody(required = true) UtenteReq req) throws CrudeliaException {
        log.debug("Create nuovo utente");
        ResponseBase r = new ResponseBase();
        r.setRc(true);
        try {
            iUtenteServices.create(req);
        } catch (CrudeliaException e) {
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return  r;
    };

    @GetMapping("/getById")
    public UtenteDTO getById(@RequestParam(required = true)Integer id) throws CrudeliaException {

        return iUtenteServices.findById(id);
    };

    @PutMapping("update")
    public ResponseBase update(@RequestBody UtenteReq req) throws CrudeliaException{
        ResponseBase r = new ResponseBase();
        try {
            iUtenteServices.update(req);
            r.setRc(true);
        } catch (CrudeliaException e) {
            r.setRc(false);
            r.setMsg(e.getMessage());
        }
        return r;
    }

    @DeleteMapping("delete")
    public ResponseBase delete(@RequestBody UtenteReq req) throws CrudeliaException{
        ResponseBase r = new ResponseBase();
        try {
            iUtenteServices.delete(req);
            r.setRc(true);
        } catch (CrudeliaException e) {
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }

    


}
