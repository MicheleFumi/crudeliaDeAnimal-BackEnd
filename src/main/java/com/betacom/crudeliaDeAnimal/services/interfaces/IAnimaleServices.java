package com.betacom.crudeliaDeAnimal.services.interfaces;

import java.util.List;

import com.betacom.crudeliaDeAnimal.dto.AnimaleDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.AnimaleReq;

public interface IAnimaleServices {
    AnimaleDTO findById(Integer id) throws CrudeliaException;
    void create(AnimaleReq req) throws CrudeliaException;
    void update(AnimaleReq req) throws CrudeliaException;
    AnimaleDTO delete(AnimaleReq req) throws CrudeliaException;
    List<AnimaleDTO> listAll() throws CrudeliaException;
     List<AnimaleDTO> findByUserId(Integer id) throws CrudeliaException;

}
