package com.betacom.crudeliaDeAnimal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.Carrello;

public interface ICarrelloRepository extends JpaRepository<Carrello, Integer> {
    Optional<Carrello> findByUtenteId(Integer utenteId);


}
