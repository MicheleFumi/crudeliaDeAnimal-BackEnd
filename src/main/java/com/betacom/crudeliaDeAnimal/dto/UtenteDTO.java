package com.betacom.crudeliaDeAnimal.dto;

import com.betacom.crudeliaDeAnimal.utils.Roles;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;



@ToString
@Builder
@Getter
@Setter
public class UtenteDTO {

	private Integer id;
	private String cognome;
	private String nome;
    private String codiceFiscale;
	private String telefono;
	private String email;
	private String indirizzo;
	private String password;
	private LocalDate dataRegistrazione;
    private Roles role;
}
