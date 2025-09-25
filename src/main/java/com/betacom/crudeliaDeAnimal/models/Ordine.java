package com.betacom.crudeliaDeAnimal.models;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ordine")
public class Ordine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)

	@JoinColumn(name = "id_utente", nullable = false)
	private Utente utente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carrello_id", nullable = false)
	private Carrello carrello;

	@Column(name = "data_ordine", nullable = false)
	private LocalDate dataOrdine;

	@Column(name = "stato_ordine", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private StatoOrdine statoOrdine;

	@Column(name = "totale_ordine", nullable = false, precision = 10, scale = 2)
	private BigDecimal totaleOrdine;

	@OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
	private List<OrdineProdotto> dettagliOrdine;

}
