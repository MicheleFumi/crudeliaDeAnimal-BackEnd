package com.betacom.crudeliaDeAnimal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class OrdineDTO {

		private Integer id;
	    private Integer idUtente;
	    private Integer idCarrello;
	    private LocalDate dataOrdine;
	    private String statoOrdine;
	    private BigDecimal totaleOrdine;
	    private List<OrdineProdottoDTO> dettagliOrdine;

}

