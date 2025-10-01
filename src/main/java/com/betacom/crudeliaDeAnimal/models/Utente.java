package com.betacom.crudeliaDeAnimal.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.betacom.crudeliaDeAnimal.utils.Roles;

import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carrello> carrelli = new ArrayList<>();

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animale> animali = new ArrayList<>();

}
