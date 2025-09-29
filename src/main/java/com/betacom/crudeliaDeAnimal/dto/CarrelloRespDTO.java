package com.betacom.crudeliaDeAnimal.dto;

import com.betacom.crudeliaDeAnimal.response.ResponseBase;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;


import java.util.List;

@ToString
@Builder
@Getter
@Setter
public class CarrelloRespDTO extends ResponseBase{
	
	Integer idUtente;
	
	BigDecimal totale;
	
	List<ProdottoDTO> prodotto;	
	
}
