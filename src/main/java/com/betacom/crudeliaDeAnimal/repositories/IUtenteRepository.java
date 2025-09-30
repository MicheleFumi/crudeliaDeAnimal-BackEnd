package com.betacom.crudeliaDeAnimal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.Utente;

public interface IUtenteRepository extends JpaRepository<Utente, Integer>{
<<<<<<< HEAD
=======
	
    Optional<Utente> findByEmailAndPassword(String email, String password);
    
    
    Optional<Utente> findByEmailOrCodiceFiscale(String email, String codiceFiscale);
>>>>>>> 26420d2dc296fb25c6b760227481668eeca10784

    Optional<Utente> findByEmailAndPassword(String email, String password);

    Optional<Utente> findByEmailOrCodiceFiscale(String email, String codiceFiscale);

    Optional<Utente> findByCodiceFiscale(String codiceFiscale);
    

    Optional<Utente> findByEmail(String email);
}
