package com.betacom.crudeliaDeAnimal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.crudeliaDeAnimal.models.MessageId;
import com.betacom.crudeliaDeAnimal.models.Messaggi;

public interface IMessageRepository extends JpaRepository<Messaggi, MessageId>{
}
