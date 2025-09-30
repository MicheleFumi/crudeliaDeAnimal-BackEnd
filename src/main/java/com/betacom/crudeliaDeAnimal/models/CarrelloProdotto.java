package com.betacom.crudeliaDeAnimal.models;




import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;
import com.betacom.crudeliaDeAnimal.utils.StatoProdotto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table (name="carrello_prodotti")
public class CarrelloProdotto {
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "carrello_id", nullable = false)
  private Carrello carrello;

  @ManyToOne
  @JoinColumn(name = "prodotto_id", nullable = false)
  private Prodotto prodotto;

  @Column(name = "stato_ordine", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private StatoProdotto statoProdotto;

  private Integer quantitaRichieste;

}
