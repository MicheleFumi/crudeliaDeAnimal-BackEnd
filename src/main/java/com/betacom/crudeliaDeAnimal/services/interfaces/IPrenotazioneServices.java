package com.betacom.crudeliaDeAnimal.services.interfaces;

import com.betacom.crudeliaDeAnimal.dto.PrenotazioneDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.PrenotazioneReq;


import java.util.List;


public interface IPrenotazioneServices {
    void create(PrenotazioneReq req) throws CrudeliaException;
    void update(PrenotazioneReq req) throws CrudeliaException;
    void delete(PrenotazioneReq req) throws CrudeliaException;
    PrenotazioneDTO findById(Integer id) throws CrudeliaException;
  List<PrenotazioneDTO> findByIdUtente(Integer id) throws CrudeliaException;
  List<PrenotazioneDTO> listAll();
}
