package com.betacom.crudeliaDeAnimal.repositories;

import com.betacom.crudeliaDeAnimal.dto.AnimaleDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.Animale;

import java.util.List;

public interface IAnimaleRepository extends JpaRepository<Animale, Integer>{
    List<Animale> findByUtenteId(Integer id);
}
