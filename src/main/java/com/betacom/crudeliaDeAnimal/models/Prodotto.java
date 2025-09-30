package com.betacom.crudeliaDeAnimal.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name="prodotto-animali")
public class Prodotto {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;

  @Column(name = "nome_prodotto",length = 255, nullable = false)
  private String nomeProdotto;

  @Column(length = 255)
  private String descrizione;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal prezzo;

  @Column(length = 100, nullable = false)
  private String categoria;

  @Column(name = "tipo_animale",length = 100, nullable = false)
  private String tipoAnimale;

  @Column(name = "quantita_disponibile",nullable = false)
  private Integer quantitaDisponibile;

  @Column(length = 500)
  private String immagineUrl;

}

