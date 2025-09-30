package com.betacom.crudeliaDeAnimal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.OrdineProdotto;

public interface IOrdineProdottoRepository extends JpaRepository<OrdineProdotto, Integer>{
    Optional<OrdineProdotto> findByOrdineIdAndProdottoId(Integer ordineId, Integer prodottoId);

	
	

}
