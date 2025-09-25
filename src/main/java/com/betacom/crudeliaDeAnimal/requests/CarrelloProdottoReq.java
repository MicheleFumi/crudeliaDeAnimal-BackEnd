package com.betacom.crudeliaDeAnimal.requests;

import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class CarrelloProdottoReq {
	
	private Integer id;

	private ProdottoDTO prodotto;
	
    private String statoProdotto;


	private Integer quantitaRicheste;
	
	

}
