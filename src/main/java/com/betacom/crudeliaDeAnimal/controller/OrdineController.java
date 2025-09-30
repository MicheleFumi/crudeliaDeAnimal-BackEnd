package com.betacom.crudeliaDeAnimal.controller;

import com.betacom.crudeliaDeAnimal.dto.OrdineDTO;
import com.betacom.crudeliaDeAnimal.requests.OrdineProdottoReq;
import com.betacom.crudeliaDeAnimal.requests.OrdineReq;

import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.implementations.ProdottoImpl;
import com.betacom.crudeliaDeAnimal.services.interfaces.IOrdineServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IProdottoServices;

import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Log4j2
@RestController
@RequestMapping("/rest/ordine")
public class OrdineController {

    public IOrdineServices IOrS;

    public OrdineController(IOrdineServices IOrS) {
        this.IOrS = IOrS;
       
    }
    @GetMapping("listAll")
   public ResponseList<OrdineDTO> listAll(){
        ResponseList<OrdineDTO> r = new ResponseList<>();

        try {
            r.setRc(true);
            r.setDati(IOrS.listAll());
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }
    
    @GetMapping("/findByUser")
    public ResponseObject<List<OrdineDTO>> findByUser(@RequestParam Integer idUtente) {
        ResponseObject<List<OrdineDTO>> r = new ResponseObject<>();
        try {
            r.setRc(true);
            r.setDati(IOrS.findByUser(idUtente));
        } catch (Exception e) {
            r.setRc(false);
            r.setMsg(e.getMessage());
        }
        return r;
    }

    @GetMapping("/findById")
    public ResponseObject<OrdineDTO> findById(@RequestParam(required = true) Integer id) {
        ResponseObject<OrdineDTO> r = new ResponseObject<OrdineDTO>();
        try {
            r.setRc(true);
            r.setDati(IOrS.findById(id));
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }

    @PostMapping("/create")
    public ResponseBase create(@RequestBody(required = true) OrdineReq req) {
        ResponseBase r = new ResponseBase();
        try {
            r.setRc(true);
            IOrS.create(req);
            r.setMsg("Ordine effettuato con successo!");    
            
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }

    @PostMapping("/deleteOrdine")

    public ResponseBase delete(@RequestBody(required = true) OrdineReq req) {
        ResponseBase r = new ResponseBase();

        try {
            r.setRc(true);
            OrdineDTO p = IOrS.delete(req);
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }

    @PostMapping("/update")
    public ResponseBase update(@RequestBody(required = true) OrdineReq req) {
        ResponseBase r = new ResponseBase();

        try {
            r.setRc(true);
            IOrS.update(req);
            r.setMsg("Ordine aggiornato con successo!");
        }catch (Exception e){
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }
    
    
}
