package com.betacom.crudeliaDeAnimal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.CarrelloProdotto;

public interface ICarrelloProdottoRepository extends JpaRepository<CarrelloProdotto, Integer>{

	Optional<CarrelloProdotto> findByCarrelloIdAndProdottoId(Integer carrelloId, Integer prodottoId);
	List<CarrelloProdotto> findByCarrelloId(Integer carrelloId);


}
