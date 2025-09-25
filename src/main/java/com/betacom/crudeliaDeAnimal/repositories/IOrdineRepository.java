package com.betacom.crudeliaDeAnimal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.Ordine;
import com.betacom.crudeliaDeAnimal.models.OrdineProdotto;

public interface IOrdineRepository extends JpaRepository<Ordine, Integer>{

}
