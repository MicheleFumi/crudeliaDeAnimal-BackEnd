package com.betacom.crudeliaDeAnimal.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betacom.crudeliaDeAnimal.models.MessageId;
import com.betacom.crudeliaDeAnimal.models.Messaggi;
import com.betacom.crudeliaDeAnimal.repositories.IMessageRepository;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;


@Log4j2
@Service
public class MessaggioImpl implements IMessaggioServices{

	private IMessageRepository msgR;
	

	public MessaggioImpl(IMessageRepository msgR) {
		super();
		this.msgR = msgR;
	}

	@Value("${lang}")
	private String lang;

	@Override
	public String getMessaggio(String code) {
		log.debug("getMessaggio :" + code);
		String r = null;
		Optional<Messaggi> m = msgR.findById(new MessageId(lang, code));
		if (m.isEmpty())
			r = code;
		else 
			r = m.get().getMessaggio();
		
		return r;
	}

}
