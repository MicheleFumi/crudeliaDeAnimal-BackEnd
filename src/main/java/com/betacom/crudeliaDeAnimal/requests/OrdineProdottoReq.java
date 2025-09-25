package com.betacom.crudeliaDeAnimal.requests;

import com.betacom.crudeliaDeAnimal.models.Prodotto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrdineProdottoReq {
    private Integer id;
    private Prodotto prodotto;
    private Integer idOrdine;
    private Integer quantita;
}
