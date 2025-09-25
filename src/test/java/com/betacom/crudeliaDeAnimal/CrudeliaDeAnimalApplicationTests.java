package com.betacom.crudeliaDeAnimal;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;


@Suite
@SelectClasses({
        VeterinarioControllerTest.class,
        UtenteControllerTest.class,
        ProdottoControllerTest.class,
        AnimaleControllerTest.class,
        PrenotazioneControllerTest.class,
        CarrelloControllerTest.class,
        CarrelloProdottoControllerTest.class,
        OrdineProdottoControllerTest.class,
        OrdineControllerTest.class


})
@SpringBootTest
class CrudeliaDeAnimalApplicationTests {

	@Test
	void contextLoads() {
	}

}
