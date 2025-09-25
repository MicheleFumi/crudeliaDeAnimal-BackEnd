package com.betacom.crudeliaDeAnimal.requests;

import java.util.List;

import com.betacom.crudeliaDeAnimal.dto.CarrelloProdottoDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarrelloReq {

	  private Integer id;

	  private Integer idUtente;

	  private String statoOrdine;

	  private List<CarrelloProdottoDTO> prodotti;
	  
}
