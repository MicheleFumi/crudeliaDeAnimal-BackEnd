package com.betacom.crudeliaDeAnimal;

import com.betacom.crudeliaDeAnimal.controller.UtenteController;
import com.betacom.crudeliaDeAnimal.dto.SignInDTO;
import com.betacom.crudeliaDeAnimal.dto.UtenteDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.SignInReq;
import com.betacom.crudeliaDeAnimal.requests.UtenteReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UtenteControllerTest {
    @Autowired
    public UtenteController utenC;

    @Test
    @Order(1)
    public void createUtenteTest() throws CrudeliaException{
        UtenteReq req= new UtenteReq();
        req.setNome("Mario");
        req.setCognome("Rossi");
        req.setCodiceFiscale("RSSMRA85T10A562S");
        req.setEmail("mario.rossi@example.com");
        req.setIndirizzo("Via Milano 22, Roma");

        req.setPassword("hashedpassword1");
        req.setTelefono("3311234567");
        req.setRole("ADMIN");
        ResponseBase r= utenC.create(req);

        req.setNome("Luigi");
        req.setCognome("Verdi");
        req.setCodiceFiscale("RSSMRA85T10A462S");
        req.setEmail("mario.rosi@examples.com");
        req.setIndirizzo("Via Milano 22, Roma");
        req.setPassword("hashedpassword2");
        req.setTelefono("3311224567");
        req.setRole("ADMIN");
        r= utenC.create(req);

        req.setNome("Paolo");
        req.setCognome("Verdi");
        req.setCodiceFiscale("RSSMRA75T10A462S");
        req.setEmail("Paolo.rossi@examples.com");
        req.setIndirizzo("Via Milano 22, Roma");
        req.setPassword("hashedpassword233");
        req.setTelefono("3312224567");
        req.setRole("ADMIN");
        r= utenC.create(req);

        Assertions.assertThat(r.getRc()).isEqualTo(true);

    }

    @Test
    @Order(2)
    public void createUtenteTestErrore() throws CrudeliaException{
        UtenteReq req= new UtenteReq();
        req.setNome("Mario");
        req.setCognome("Rossi");
        req.setCodiceFiscale("RSSMRA85T10A562S");
        req.setEmail("mario.rossi@examsple.com");
        req.setIndirizzo("Via Milano 22s, Roma");
        req.setPassword("hashedpassword2");
        req.setTelefono("331123456s7");
        req.setRole("ADMIN");
        ResponseBase r= utenC.create(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("USER_DUPLICATE_CodiceFiscale");

        req.setNome("Mario");
        req.setCognome("Rossi");
        req.setCodiceFiscale("RMBMRA85T10A562S");
        req.setEmail("mario.rossi@example.com");
        req.setIndirizzo("Via Milano 22s, Roma");
        req.setPassword("hashedpassword2");
        req.setTelefono("331123456s7");
        req.setRole("ADMIN");
         r= utenC.create(req);

        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("USER_DUPLICATE_EMAIL");




    }

    @Test
    @Order(3)
    public void listAllTest() throws CrudeliaException{
        ResponseList<UtenteDTO> r = utenC.listAll();
        Assertions.assertThat(r.getRc()).isEqualTo(true);
        Assertions.assertThat(r.getDati()).isNotEmpty();
    }

    @Test
    @Order(4)
    public void findByIdTest() throws CrudeliaException{

        Assertions.assertThat( utenC.getById(1)).isNotNull();
    }

    @Test
    @Order(5)
    public void findByIdTestErrore() throws CrudeliaException{

        Assertions.assertThatThrownBy(() -> utenC.getById(344))
                .isInstanceOf(CrudeliaException.class)                   // controlla il tipo
                .hasMessage("Utente non esistente");
    }

    @Test
    @Order(6)
    public void updateUtenteTest()throws CrudeliaException{
        UtenteReq req= new UtenteReq();
        req.setId(1);
        req.setNome("Maria");
        req.setCognome("Rossini");
        req.setCodiceFiscale("RSSMRA85T10A562S");
        req.setEmail("mario.rossi@example.com");
        req.setIndirizzo("Via Milano 22, Roma");
        req.setPassword("hashedpassword1");
        req.setTelefono("3311234567");
        req.setRole("ADMIN");
        ResponseBase r= utenC.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(true);


    }

    @Test
    @Order(7)
    public void updateUtenteTestErrore()throws CrudeliaException{
        UtenteReq req= new UtenteReq();
        req.setId(121);
        req.setNome("Maria");
        req.setCognome("Rossini");
        req.setCodiceFiscale("RSSMRA85T10A562S");
        req.setEmail("mario.rossi@example.com");
        req.setIndirizzo("Via Milano 22, Roma");
        req.setPassword("hashedpassword1");
        req.setTelefono("3311234567");
        req.setRole("ADMIN");
        ResponseBase r= utenC.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("Utente non esistente");

    }

    @Test
    @Order(8)
    public void signinTest()throws CrudeliaException{
        SignInReq req = new SignInReq();
        req.setEmail("mario.rossi@example.com");
        req.setPassword("hashedpassword1");

        SignInDTO dto = utenC.signin(req);

        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getLogged()).isTrue();  // controlla il campo logged
        Assertions.assertThat(dto.getId()).isNotNull();
        Assertions.assertThat(dto.getRole()).isNotNull();

    }

    @Test
    @Order(9)
    public void signinTestErrore()throws CrudeliaException{
        SignInReq req = new SignInReq();
        req.setEmail("mario.rossi@example.com");
        req.setPassword("wrongpassword");

        SignInDTO dto = utenC.signin(req);

        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getLogged()).isFalse();  // controlla il campo logged
        Assertions.assertThat(dto.getId()).isNull();
        Assertions.assertThat(dto.getRole()).isNull();
    }

























}
