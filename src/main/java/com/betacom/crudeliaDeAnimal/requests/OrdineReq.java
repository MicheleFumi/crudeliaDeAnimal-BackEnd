package com.betacom.crudeliaDeAnimal.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@ToString
public class OrdineReq {
    private Integer id;
    private Integer idUtente;
    private Integer idCarrello;
    private LocalDate dataOrdine;
    private String statoOrdine;
    private BigDecimal totaleOrdine;
    private List<OrdineProdottoReq> dettagliOrdine;
   
}
