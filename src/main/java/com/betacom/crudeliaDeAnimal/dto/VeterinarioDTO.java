package com.betacom.crudeliaDeAnimal.dto;

import com.betacom.crudeliaDeAnimal.utils.ServizioVeterinarioOspedale;
import com.betacom.crudeliaDeAnimal.utils.StruttureSanitarie;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class VeterinarioDTO {

	private Integer id;
	private String nome;
	private StruttureSanitarie tipostruttura; // "Clinica", "Ospedale"
	private String indirizzo;
	private String provincia;
	private String regione;
	private String cap;
	private String telefono;
	private String email;
	private String orariApertura;
	private ServizioVeterinarioOspedale serviziVO;

}
