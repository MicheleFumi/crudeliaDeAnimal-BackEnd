package com.betacom.crudeliaDeAnimal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.Prodotto;

public interface IProdottoRepository extends JpaRepository<Prodotto, Integer>{
	
	Optional<Prodotto> findByNomeProdotto(String nomeProdotto);

}
