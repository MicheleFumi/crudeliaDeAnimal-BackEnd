package com.betacom.crudeliaDeAnimal.requests;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdottoReq {

	private Integer id;

	private String nomeProdotto;

	private String descrizione;

	private BigDecimal prezzo;

	private String categoria;

	private String tipoAnimale;

	private Integer quantitaDisponibile;

	private String immagineUrl;
}
