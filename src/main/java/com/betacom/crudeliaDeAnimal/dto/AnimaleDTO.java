package com.betacom.crudeliaDeAnimal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Builder
@Getter
@Setter
public class AnimaleDTO {

	private Integer id;
    private UtenteDTO utente;
    private String nomeAnimale;
    private String tipo;
    private String razza;
    private String noteMediche;
}
