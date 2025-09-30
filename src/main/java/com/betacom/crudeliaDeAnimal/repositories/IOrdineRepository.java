package com.betacom.crudeliaDeAnimal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.crudeliaDeAnimal.models.Ordine;
import com.betacom.crudeliaDeAnimal.models.OrdineProdotto;
import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;

public interface IOrdineRepository extends JpaRepository<Ordine, Integer>{
	
	Optional<Ordine> findByUtente_IdAndStatoOrdine(Integer idUtente , StatoOrdine statoOrdine);
	
	 @Query("SELECT DISTINCT o FROM Ordine o " +
	           "LEFT JOIN FETCH o.dettagliOrdine d " +
	           "LEFT JOIN FETCH d.prodotto " +
	           "WHERE o.utente.id = :idUtente")
	    List<Ordine> findByUtenteId(@Param("idUtente") Integer idUtente);


}
