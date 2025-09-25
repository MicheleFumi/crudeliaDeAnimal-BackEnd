package com.betacom.crudeliaDeAnimal;

import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.betacom.crudeliaDeAnimal.controller.ProdottoController;
import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;
import com.betacom.crudeliaDeAnimal.dto.VeterinarioDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.repositories.IProdottoRepository;
import com.betacom.crudeliaDeAnimal.requests.ProdottoReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;

@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdottoControllerTest {
	@Autowired
	private ProdottoController proCont;
	
	@Autowired
    private IProdottoRepository iproRep;
	
	
	@Test
    @Order(1)
    public void createProdottoTest() throws CrudeliaException {
        log.debug("create Prodotto!");
        
        ProdottoReq req= new ProdottoReq();
     
        req.setNomeProdotto("ciao");
        req.setDescrizione("aaaaa.");
        req.setPrezzo(new BigDecimal("90.80"));
        req.setCategoria("Accessori");
        req.setTipoAnimale("Gatto");
        req.setQuantitaDisponibile(50);
        req.setImmagineUrl("prova bella ");
		
        
        ResponseBase r = proCont.create(req);
        
        Assertions.assertThat(r.getRc()).isEqualTo(true);
        
        
		/*
		 * ProdottoReq req2 = new ProdottoReq();
		 * 
		 * req2.setNomeProdotto("Mangime per Pesci Tropicali"); req2.
		 * setDescrizione("Alimento completo per pesci tropicali, ricco di nutrienti essenziali"
		 * ); req2.setPrezzo(new BigDecimal("45.60"));
		 * req2.setCategoria("Alimentazione"); req2.setTipoAnimale("Pesce");
		 * req2.setQuantitaDisponibile(200); req2.setImmagineUrl("pesce.png ");
		 * 
		 * 
		 * r = proCont.create(req2); Assertions.assertThat(r.getRc()).isEqualTo(true);
		 */
        ProdottoReq req2 = new ProdottoReq();
        req2.setNomeProdotto("Cibo per Gatti Adulti");
        req2.setDescrizione("Alimento bilanciato per gatti adulti, con proteine di alta qualità");
        req2.setPrezzo(new BigDecimal("32.90"));
        req2.setCategoria("Alimentazione");
        req2.setTipoAnimale("Gatto");
        req2.setQuantitaDisponibile(150);
        req2.setImmagineUrl("gatto.png");
        r = proCont.create(req2); 
        Assertions.assertThat(r.getRc()).isEqualTo(true);


	}
	
	@Test
    @Order(2)
    public void createProdottoErroreTest() throws CrudeliaException {
        log.debug("create Veterinario Errore!");
        
        ProdottoReq req= new ProdottoReq();
        
      
        req.setNomeProdotto("Tiragraffi");
        req.setDescrizione("Tiragraffi a più livelli con cuccia e giochi, ideale per gatti attivi.");
        req.setPrezzo(new BigDecimal("90.80"));
        req.setCategoria("Accessori");
        req.setTipoAnimale("Gatto");
        req.setQuantitaDisponibile(0);
        req.setImmagineUrl("https://www.giordanoshop.com/800x.jpg");
        
        ResponseBase r = proCont.create(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("QUANTITY_GREATER_THAN_ZERO");
        
       
        req.setNomeProdotto("Tiragraffi Deluxe");
        req.setDescrizione("Tiragraffi a più livelli con cuccia e giochi, ideale per gatti attivi.");
        req.setPrezzo(new BigDecimal("90.80"));
        req.setCategoria("Accessori");
        req.setTipoAnimale(null);
        req.setQuantitaDisponibile(30);
        req.setImmagineUrl("https://www.giordanoshop.com/cdn/shop/files/2_2F1_2F0_2F3_2F0_2F210303-1_3bb7580d-ddbb-446b-a9e4-42c34a5769c5_800x.jpg");
        
        r = proCont.create(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("PRODUCT_MISSING_FIELDS");
            
        req.setId(1);
        req.setNomeProdotto("ciao");
        req.setDescrizione("aaaaa.");
        req.setPrezzo(new BigDecimal("90.80"));
        req.setCategoria("Accessori");
        req.setTipoAnimale("Gatto");
        req.setQuantitaDisponibile(50);
        req.setImmagineUrl("prova bella ");
        
        r = proCont.create(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("PRODUCT_DUPLICATE");
	}
	
	@Test
    @Order(3)
    public void updateProdottiControllerTest() throws CrudeliaException {
       
		ProdottoReq req= new ProdottoReq();
        req.setId(1);
        req.setNomeProdotto("ciao John");
        req.setDescrizione("aaaaa.");
        req.setPrezzo(new BigDecimal("90.80"));
        req.setCategoria("Accessori");
        req.setTipoAnimale("Gatto");
        req.setQuantitaDisponibile(50);
        req.setImmagineUrl("prova bella ");
        
        ResponseBase r = proCont.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(true);
        
        
        

        
        req.setId(4);
        req.setNomeProdotto("ciao John");
        req.setDescrizione("aaaaa.");
        req.setPrezzo(new BigDecimal("90.80"));
        req.setCategoria("Accessori");
        req.setTipoAnimale("Gatto");
        req.setQuantitaDisponibile(50);
        req.setImmagineUrl("prova bella ");
        
        r = proCont.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("PRODUCT_NOT_FOUND");



	
	}
	
	
	
	@Test
    @Order(4)
    public void listAllProdottiControllerTest() throws CrudeliaException {
		
		ResponseBase resp=proCont.listAll();
		
		// controlla che il controller abbia risposto correttamente
	    Assertions.assertThat(resp).isInstanceOf(ResponseList.class);
	    ResponseList<?> r = (ResponseList<?>) resp;

	    Assertions.assertThat(r.getRc()).isEqualTo(true);
	    Assertions.assertThat(r.getDati()).isInstanceOf(List.class);

	    
	    List<?> lista = (List<?>) r.getDati();
	    Assertions.assertThat(lista).isNotEmpty();
	}
	
	
	@Test
    @Order(5)
    public void findByIdProdottiControllerTest() throws CrudeliaException {
		
		Integer id =1;
		
		ResponseObject<ProdottoDTO>  resp=proCont.findById(id);
			    
	    Assertions.assertThat(resp.getRc()).isEqualTo(true);
	    
	    Assertions.assertThat(resp.getDati()).isNotNull();
	    
	    Assertions.assertThat(resp.getDati().getId()).isEqualTo(id);


	    id=3;
	    resp=proCont.findById(id);
	    Assertions.assertThat(resp.getRc()).isEqualTo(false);
	    Assertions.assertThat(resp.getDati()).isNull();
        Assertions.assertThat(resp.getMsg()).isEqualTo("PRODUCT_NOT_FOUND");



	    



	}
	
	@Test
    @Order(6)
    public void deleteProdottiControllerTest() throws CrudeliaException {
		
		ProdottoReq req= new ProdottoReq();
		
		req.setId(4);
        req.setNomeProdotto("ciao John");
        req.setDescrizione("aaaaa.");
        req.setPrezzo(new BigDecimal("90.80"));
        req.setCategoria("Accessori");
        req.setTipoAnimale("Gatto");
        req.setQuantitaDisponibile(50);
        req.setImmagineUrl("prova bella ");
        
        ResponseBase r = proCont.delete(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("PRODUCT_NOT_FOUND");

        

        req.setId(1);
        req.setNomeProdotto("ciao John");
        req.setDescrizione("aaaaa.");
        req.setPrezzo(new BigDecimal("90.80"));
        req.setCategoria("Accessori");
        req.setTipoAnimale("Gatto");
        req.setQuantitaDisponibile(50);
        req.setImmagineUrl("prova bella ");
        
         r = proCont.delete(req);
        Assertions.assertThat(r.getRc()).isEqualTo(true);
        
        
        r=proCont.listAll();
        Assertions.assertThat(r).isInstanceOf(ResponseList.class);
	    ResponseList<?> rList = (ResponseList<?>) r;

	    Assertions.assertThat(rList.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo("PRODUCT_NOT_FOUND");

        


	}	

}
