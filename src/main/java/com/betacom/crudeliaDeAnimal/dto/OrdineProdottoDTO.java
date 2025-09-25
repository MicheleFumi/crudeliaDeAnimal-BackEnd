package com.betacom.crudeliaDeAnimal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class OrdineProdottoDTO {

	private Integer id;
	private Integer ordineId;
	private ProdottoDTO prodotto;
	private Integer quantita;
}
