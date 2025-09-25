package com.betacom.crudeliaDeAnimal.services.interfaces;


import com.betacom.crudeliaDeAnimal.dto.CarrelloDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;

public interface ICarrelloServices {
	
	CarrelloDTO getCarrelloByUtente(Integer idUtente , boolean createIfMissing) throws CrudeliaException;

    void emptyCarrello(Integer idUtente) throws CrudeliaException;
}
