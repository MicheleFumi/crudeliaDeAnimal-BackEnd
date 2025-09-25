package com.betacom.crudeliaDeAnimal.services.interfaces;

import java.util.List;

import com.betacom.crudeliaDeAnimal.dto.OrdineProdottoDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.OrdineProdottoReq;

public interface IOrdineProdottoServices {
	
    void create(OrdineProdottoReq req) throws CrudeliaException;
    
    OrdineProdottoDTO findById(Integer id) throws CrudeliaException;
    
    void update(OrdineProdottoReq req) throws CrudeliaException;
    
    OrdineProdottoDTO delete(OrdineProdottoReq req) throws CrudeliaException;
    
    List<OrdineProdottoDTO> listAll();
}
