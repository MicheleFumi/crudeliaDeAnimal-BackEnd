package com.betacom.crudeliaDeAnimal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.Veterinario;

public interface IVeterinarioRepository extends JpaRepository<Veterinario, Integer>{
	Optional<Veterinario> findByNome(String nome);
	List<Veterinario> findByProvincia(String provincia);
	List<Veterinario> findByRegione(String regione);
	List<Veterinario> findByCap(String cap);

  List<Veterinario> findByUtente_id(String utente);

}
