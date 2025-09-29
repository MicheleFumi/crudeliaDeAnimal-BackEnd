package com.betacom.crudeliaDeAnimal.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class ProdottoDTO {

	private Integer id;
    private String nomeProdotto;
    private String descrizione;
    private BigDecimal prezzo;
    private String categoria;
    private String tipoAnimale;
    private Integer quantitaDisponibile;
	private Integer quantitaRicheste;
    private String immagineUrl;

}

