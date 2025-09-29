package com.betacom.crudeliaDeAnimal.services.interfaces;

import com.betacom.crudeliaDeAnimal.dto.CarrelloDTO;
import com.betacom.crudeliaDeAnimal.dto.CarrelloRespDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.CarrelloProdottoReq;

public interface ICarrelloProdottoServices {
	
	CarrelloDTO createProdotto(Integer idUtente, CarrelloProdottoReq prodottoReq) throws CrudeliaException;

    CarrelloDTO updateProdotto(Integer idUtente, CarrelloProdottoReq prodottoReq) throws CrudeliaException;

   //  String deleteProdotto(Integer idUtente, Integer idProdotto) throws CrudeliaException;
    
    //CarrelloRespDTO deleteProdotto(Integer idUtente, Integer idProdotto) throws CrudeliaException;
    
    CarrelloDTO deleteProdotto(Integer idUtente, Integer idProdotto) throws CrudeliaException;

}
