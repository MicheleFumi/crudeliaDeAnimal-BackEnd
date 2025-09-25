package com.betacom.crudeliaDeAnimal.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class CarrelloDTO {

	 private Integer id;

	  private Integer idUtente;

	    private String statoOrdine;

	  private List<CarrelloProdottoDTO> prodotti;
	 
}
