package com.betacom.crudeliaDeAnimal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Animale")
public class Animale {

	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @Column(name = "nome_animale",length = 100, nullable = false)
    private String nomeAnimale;

    @Column(length = 100, nullable = false)
    private String tipo;
    
    
    @Column(length = 100, nullable = false)
    private String razza;
    
    
    @Column(name = "note_mediche",length = 255, nullable = false)
    private String noteMediche;
	
	
}
