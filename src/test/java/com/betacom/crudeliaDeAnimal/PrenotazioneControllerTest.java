package com.betacom.crudeliaDeAnimal;

import com.betacom.crudeliaDeAnimal.controller.PrenotazioneController;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.PrenotazioneReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.utils.StatoVisita;
import com.betacom.crudeliaDeAnimal.utils.TipoPagamenti;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrenotazioneControllerTest {
    @Autowired
    private PrenotazioneController preC;



    @Test
    @Order(1)
    public void createPrenotazioneTest()throws CrudeliaException{
        PrenotazioneReq req= new PrenotazioneReq();
        req.setIdAnimale(2);
        req.setIdUtente(3);
        req.setIdVeterinario(1);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("visita");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

       ResponseBase r= preC.create(req);
        Assertions.assertThat(r.getRc()).isEqualTo(true);

        req.setIdAnimale(2);
        req.setIdUtente(2);
        req.setIdVeterinario(1);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("visita");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

         r= preC.create(req);
        Assertions.assertThat(r.getRc()).isEqualTo(true);

    }

    @Test
    @Order(2)
    public void createPrenotazioneTestErrore()throws CrudeliaException{
        PrenotazioneReq req= new PrenotazioneReq();
        req.setIdAnimale(1032323000);
        req.setIdUtente(3);
        req.setIdVeterinario(1);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("visita");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

        ResponseBase r= preC.create(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("ANIMAL_NOT_FOUND");


        req.setIdAnimale(2);
        req.setIdUtente(10000);
        req.setIdVeterinario(1);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("visita");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

        r= preC.create(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("USER_NOT_FOUND");


        req.setIdAnimale(2);
        req.setIdUtente(3);
        req.setIdVeterinario(10000);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("visita");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

        r= preC.create(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");

    }

    @Test
    @Order(3)
    public void updatePrenotazioneTest()throws CrudeliaException{
        PrenotazioneReq req= new PrenotazioneReq();
        req.setId(1);
        req.setIdAnimale(2);
        req.setIdUtente(3);
        req.setIdVeterinario(1);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("prova");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

        ResponseBase r= preC.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(true);

    }

    @Test
    @Order(4)
    public void updatePrenotazioneTestErrore()throws CrudeliaException{
        PrenotazioneReq req= new PrenotazioneReq();

        req.setIdAnimale(2);
        req.setIdUtente(1);
        req.setIdVeterinario(1);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("visita");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

        ResponseBase r= preC.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("The given id must not be null");

        req.setId(1);
        req.setIdAnimale(2);
        req.setIdUtente(1);
        req.setIdVeterinario(10000);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("visita");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

        r= preC.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");

    }

    @Test
    @Order(5)
    public void deletePrenotazioneTestErrore()throws CrudeliaException{
        PrenotazioneReq req= new PrenotazioneReq();
        req.setId(10000);
        req.setIdAnimale(2);
        req.setIdUtente(3);
        req.setIdVeterinario(1);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("prova");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

        ResponseBase r= preC.delete(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("VISIT_NOT_FOUND");
    }

    @Test
    @Order(6)
    public void findByIdPrenotazioneTest()throws CrudeliaException{

        Assertions.assertThat(preC.findById(1)).isNotNull();
    }

    @Test
    @Order(7)
    public void findByIdPrenotazioneTestErrore()throws CrudeliaException{

        ResponseObject<?> r = preC.findById(100000);

        Assertions.assertThat(r.getRc()).isFalse();
        Assertions.assertThat(r.getMsg()).contains("VISIT_NOT_FOUND");
        Assertions.assertThat(r.getDati()).isNull();
    }

    @Test
    @Order(8)
    public void listAllPrenotazioneTest()throws CrudeliaException{

        Assertions.assertThat( preC.listAll()).isNotNull();
    }

    @Test
    @Order(9)
    public void deletePrenotazioneTest()throws CrudeliaException{
        PrenotazioneReq req= new PrenotazioneReq();
        req.setId(1);
        req.setIdAnimale(2);
        req.setIdUtente(3);
        req.setIdVeterinario(1);
        req.setDataVisita(LocalDate.now());
        req.setOraVisita(LocalTime.now());
        req.setMotivoVisita("prova");
        req.setStatoVisita(StatoVisita.CONFERMATA);
        req.setTipoPagamento(TipoPagamenti.CARTA);

        ResponseBase r= preC.delete(req);
        Assertions.assertThat(r.getRc()).isEqualTo(true);

    }












































































}
