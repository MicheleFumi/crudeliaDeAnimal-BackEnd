package com.betacom.crudeliaDeAnimal.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseList<T> extends ResponseBase{

	private List<T> dati;
}
