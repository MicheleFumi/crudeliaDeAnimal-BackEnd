package com.betacom.crudeliaDeAnimal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class CarrelloProdottoDTO {
	


	private Integer id;

	private ProdottoDTO prodotto;
	
    private String statoProdotto;

	private Integer quantitaRicheste;

}
