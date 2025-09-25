package com.betacom.crudeliaDeAnimal.services.interfaces;

import com.betacom.crudeliaDeAnimal.dto.SignInDTO;
import com.betacom.crudeliaDeAnimal.dto.UtenteDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.SignInReq;
import com.betacom.crudeliaDeAnimal.requests.UtenteReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;

import java.util.List;


public interface IUtenteServices {
    void create(UtenteReq req) throws CrudeliaException;
    void update(UtenteReq req) throws CrudeliaException;
    void delete(UtenteReq req) throws CrudeliaException;
    List<UtenteDTO> listAll();
    UtenteDTO findById(Integer id) throws CrudeliaException;
    SignInDTO signIn(SignInReq req) throws CrudeliaException;
}
