package com.betacom.crudeliaDeAnimal.requests;

import com.betacom.crudeliaDeAnimal.models.Utente;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class VeterinarioReq {

	private Integer id;

  private Utente utente;

	private String nome;

	private String tipostrutture; // "Clinica", "Ospedale"

	private String indirizzo;

	private String provincia;

	private String regione;

	private String cap;

	private String telefono;

	private String email;

	private String orariApertura;

	private String serviziVO;
}
