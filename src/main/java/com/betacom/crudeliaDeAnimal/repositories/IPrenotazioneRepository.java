package com.betacom.crudeliaDeAnimal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.Prenotazione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface IPrenotazioneRepository extends JpaRepository<Prenotazione, Integer>{
    @Query("SELECT p.oraVisita FROM Prenotazione p " +
            "WHERE p.veterinario.id = :vetId " +
            "AND p.dataVisita = :data " +
            "AND p.statoVisita IN ('CONFERMATA', 'IN_LAVORAZIONE')")
    List<LocalTime> findOrariOccupati(
            @Param("vetId") Integer vetId,
            @Param("data") LocalDate data
    );


}
