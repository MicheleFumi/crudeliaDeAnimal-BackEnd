package com.betacom.crudeliaDeAnimal.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name="messaggi_systema")
public class Messaggi {
	
	@EmbeddedId
	private MessageId msgId;
	
	private String messaggio;
	

}