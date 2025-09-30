package com.betacom.crudeliaDeAnimal.models;

import com.betacom.crudeliaDeAnimal.utils.ServizioVeterinarioOspedale;
import com.betacom.crudeliaDeAnimal.utils.StruttureSanitarie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "veterinario-ospedali")
public class Veterinario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 255)
	private String nome;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_utente", nullable = true)
  private Utente utente;

	@Column(name = "strutture_sanitarie" ,nullable = false, length = 255)
	@Enumerated(EnumType.STRING)
	private StruttureSanitarie tipostrutture; // "Clinica", "Ospedale"

	@Column(nullable = false, length = 255)
	private String indirizzo;

	@Column(nullable = false, length = 255)
	private String provincia;

	@Column(nullable = false, length = 255)
	private String regione;

	@Column(nullable = false, length = 255)
	private String cap;


	@Column(nullable = false, length = 20)
	private String telefono;

	@Column(nullable = false, length = 255)
	private String email;

	@Column(name = "orari_apertura", length = 255, nullable = false)
	private String orariApertura;

	@Column(name = "servizi_Offerti", length = 255, nullable = false)
	@Enumerated(EnumType.STRING)
	private ServizioVeterinarioOspedale serviziVO;

}
