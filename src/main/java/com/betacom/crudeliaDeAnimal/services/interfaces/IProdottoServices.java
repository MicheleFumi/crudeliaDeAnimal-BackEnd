package com.betacom.crudeliaDeAnimal.services.interfaces;

import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.ProdottoReq;

import java.util.List;

public interface IProdottoServices {
    void create(ProdottoReq req) throws CrudeliaException;
    void update(ProdottoReq req) throws CrudeliaException;
    ProdottoDTO delete(ProdottoReq req) throws CrudeliaException;
    List<ProdottoDTO> listAll() throws CrudeliaException;
    ProdottoDTO findById(Integer id) throws CrudeliaException;    
}
