package com.betacom.crudeliaDeAnimal.requests;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UtenteReq {

	private Integer id;

	private String Cognome;

	private String nome;

	private String codiceFiscale;

	private String telefono;

	private String email;

	private String indirizzo;

	private String password;

	private LocalDate dataRegistrazione;

	private String role;

}
