package com.betacom.crudeliaDeAnimal.models;

import java.time.LocalDate;

import com.betacom.crudeliaDeAnimal.utils.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
@Table(name = "Utente")
public class Utente {


	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;

  @Column(length = 255, nullable = false)
	private String cognome;

  @Column(length = 255, nullable = false)
	private String nome;

  @Column(name = "codice_fiscale", length = 16, nullable = false, unique = true)
  private String codiceFiscale;

  @Column(length = 255, nullable = false)
	private String telefono;

  @Column(length = 255, nullable = false)
	private String  email;

  @Column(length = 255, nullable = false)
	private String  indirizzo;

  @Column(length = 255, nullable = false)
	private String password;

  @Column(name = "data_registrazione",length = 255, nullable = false)
	private LocalDate  dataRegistrazione;

	@Column(name = "role", length = 255, nullable = false)
	@Enumerated(EnumType.STRING)
    private Roles role;
}
