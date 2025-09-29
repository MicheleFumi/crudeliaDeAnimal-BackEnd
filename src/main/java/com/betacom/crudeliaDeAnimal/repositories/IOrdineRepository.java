package com.betacom.crudeliaDeAnimal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.Ordine;
import com.betacom.crudeliaDeAnimal.models.OrdineProdotto;
import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;

public interface IOrdineRepository extends JpaRepository<Ordine, Integer>{
	
	Optional<Ordine> findByUtente_IdAndStatoOrdine(Integer idUtente , StatoOrdine statoOrdine);


}
