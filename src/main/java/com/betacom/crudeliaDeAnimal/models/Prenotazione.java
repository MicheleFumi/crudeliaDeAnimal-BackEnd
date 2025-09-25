package com.betacom.crudeliaDeAnimal.models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.betacom.crudeliaDeAnimal.utils.StatoVisita;
import com.betacom.crudeliaDeAnimal.utils.TipoPagamenti;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name="prenotazione_visita")
public class Prenotazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	 @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "id_utente", nullable = false)
	    private Utente utente;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "id_animale", nullable = false)
	    private Animale animale;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "id_veterinario", nullable = false)
	    private Veterinario veterinario;

	    @Column(name = "data_visita",nullable = false)
	    private LocalDate dataVisita;

	    @Column(name = "ora_visita",nullable = false)
	    private LocalTime oraVisita;

	    @Column(name = "motivo_visita",nullable = false, length = 255)
	    private String motivoVisita;

	    @Column(name = "stato_visita",nullable = false, length = 50)
		@Enumerated(EnumType.STRING)
	    private StatoVisita statoVisita;
	    
	    @Column(name = "tipo_pagamento",nullable = false, length = 50)
		@Enumerated(EnumType.STRING)
	    private TipoPagamenti tipoPagamento;  // online , inSede

}
