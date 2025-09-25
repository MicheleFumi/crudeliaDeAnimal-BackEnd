package com.betacom.crudeliaDeAnimal.services.interfaces;

import com.betacom.crudeliaDeAnimal.dto.OrdineDTO;

import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.OrdineReq;

import java.util.List;

public interface IOrdineServices {
	
    OrdineDTO findById(Integer id) throws CrudeliaException;
    void create(OrdineReq req) throws CrudeliaException;
    void update(OrdineReq req) throws CrudeliaException;
    OrdineDTO delete(OrdineReq req) throws CrudeliaException;
    List<OrdineDTO> listAll();
}
