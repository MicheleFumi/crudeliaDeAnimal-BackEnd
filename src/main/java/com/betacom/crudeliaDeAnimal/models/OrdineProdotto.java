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
@Table (name="ordine_Prodotto")
public class OrdineProdotto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_utente", nullable = false)
  private Utente utente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ordine", nullable = false)
	private Ordine ordine;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prodotto", nullable = false)
	private Prodotto prodotto;

	@Column(nullable = false)
	private Integer quantita;
}
