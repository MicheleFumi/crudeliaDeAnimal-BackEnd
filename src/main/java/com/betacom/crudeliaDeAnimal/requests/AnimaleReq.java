package com.betacom.crudeliaDeAnimal.requests;

import com.betacom.crudeliaDeAnimal.models.Utente;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnimaleReq {

    private Integer id;
    private Utente utente;
    private String nomeAnimale;
    private String tipo;
    private String razza;
    private String noteMediche;
}
