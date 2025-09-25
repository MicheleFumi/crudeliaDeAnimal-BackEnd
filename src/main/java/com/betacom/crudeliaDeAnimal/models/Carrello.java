package com.betacom.crudeliaDeAnimal.models;


import java.util.ArrayList;
import java.util.List;

import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "carrelli")
public class Carrello {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @OneToOne
	    @JoinColumn(name = "utente_id", nullable = false)
	    private Utente utente;

	    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    private List<CarrelloProdotto> prodotti = new ArrayList<>();
	    
	    @Column(name = "stato_ordine", length = 50, nullable = false)
		@Enumerated(EnumType.STRING)
		private StatoOrdine statoOrdine;
	   
}
