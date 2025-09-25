package com.betacom.crudeliaDeAnimal.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.crudeliaDeAnimal.dto.OrdineProdottoDTO;
import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Ordine;
import com.betacom.crudeliaDeAnimal.models.OrdineProdotto;
import com.betacom.crudeliaDeAnimal.models.Prodotto;
import com.betacom.crudeliaDeAnimal.repositories.IOrdineProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.IOrdineRepository;
import com.betacom.crudeliaDeAnimal.repositories.IProdottoRepository;
import com.betacom.crudeliaDeAnimal.requests.OrdineProdottoReq;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IOrdineProdottoServices;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class OrdineProdottoImpl implements IOrdineProdottoServices {
	
	private IOrdineProdottoRepository ordineProdottoRepo;
	private IOrdineRepository ordineRepo;
	private IProdottoRepository prodottoRepo;
	private IMessaggioServices  msgS;

    

	public OrdineProdottoImpl(IOrdineProdottoRepository ordineProdottoRepo, IOrdineRepository ordineRepo,
			IProdottoRepository prodottoRepo ,IMessaggioServices  msgS) {
		super();
		this.ordineProdottoRepo = ordineProdottoRepo;
		this.ordineRepo = ordineRepo;
		this.prodottoRepo = prodottoRepo;
		this.msgS=msgS;
	}
	

	@Override
	public void create(OrdineProdottoReq req) throws CrudeliaException {
		
		log.debug("Create:" + req);
		
		 if (req.getQuantita() == null || req.getQuantita() <= 0) {
	   			throw new CrudeliaException(msgS.getMessaggio("ORDER_ITEM_NOT_AVAILABLE"));
		    }
		 
		  if (req.getProdotto() == null || req.getProdotto().getId() == null) {
	   			throw new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_FOUND"));
		    }
 
		  //  Optional<Ordine> ordineOpt = ordineRepo.findById(req.getId());
		  Optional<Ordine> ordineOpt = ordineRepo.findById(req.getIdOrdine());

		    
		    if (ordineOpt.isEmpty()) {
	   			throw new CrudeliaException(msgS.getMessaggio("ORDER_NOT_FOUND"));
		    }
		    
		  
		Optional<Prodotto> prodottoOpt = prodottoRepo.findById(req.getProdotto().getId());
		
		  if (prodottoOpt.isEmpty()) {
	   			throw new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_FOUND"));
		    }


	  OrdineProdotto ordProdotto= new OrdineProdotto();
	  ordProdotto.setOrdine(ordineOpt.get());
	  ordProdotto.setProdotto(prodottoOpt.get());
	  ordProdotto.setQuantita(req.getQuantita());
	  
	  ordineProdottoRepo.save(ordProdotto);
		
	}

	@Override

	public OrdineProdottoDTO findById(Integer id) throws CrudeliaException {
		
		log.debug("findById:" + id);
		
		Optional<OrdineProdotto> ordineProdotto=ordineProdottoRepo.findById(id);
		
		if(ordineProdotto.isEmpty())
   			throw new CrudeliaException(msgS.getMessaggio("ORDER_NOT_FOUND"));
		
		
		return  toOrdineProdotto(ordineProdotto.get());
				
	}

	@Override
	public void update(OrdineProdottoReq req) throws CrudeliaException {
	   
		log.debug("Update: " + req);
		
		 
		Optional<OrdineProdotto> ordineProdotto=ordineProdottoRepo.findById(req.getId());
		
		if(ordineProdotto.isEmpty())
   			throw new CrudeliaException(msgS.getMessaggio("ORDER_NOT_FOUND"));
		
		
		/*
		 * if(req.getProdotto() !=null)
		 * 
		 * ordineProdotto.get().setProdotto(req.getProdotto());
		 */
		
		if(req.getQuantita() !=null) 
		{
			 if (req.getQuantita() <= 0) 
			 {
		   			throw new CrudeliaException(msgS.getMessaggio("QUANTITY_GREATER_THAN_ZERO"));
			 }
			ordineProdotto.get().setQuantita(req.getQuantita());
		}
		ordineProdottoRepo.save(ordineProdotto.get());
	}
	

	@Override
	public OrdineProdottoDTO delete(OrdineProdottoReq req) throws CrudeliaException {
		
		log.debug("remove:" + req);
		
		Optional<OrdineProdotto> ordineProdotto=ordineProdottoRepo.findById(req.getId());

		if(ordineProdotto.isEmpty())
   			throw new CrudeliaException(msgS.getMessaggio("ORDER_NOT_FOUND"));
		
		ordineProdottoRepo.delete(ordineProdotto.get());

		
		return  toOrdineProdotto(ordineProdotto.get());
				
				
		
	}

	@Override
	public List<OrdineProdottoDTO> listAll() {
		log.debug("listAll");
		
		List<OrdineProdotto>  lOrdPro=ordineProdottoRepo.findAll();
		
		return lOrdPro.stream()
				.map(this::toOrdineProdotto)
				.collect(Collectors.toList());

	}
	
	private OrdineProdottoDTO toOrdineProdotto(OrdineProdotto ordPro) {
		
		ProdottoDTO proDTO= null;
		if(ordPro.getProdotto() != null)
		{
			proDTO=ProdottoDTO.builder()
					.id(ordPro.getProdotto().getId())
					.nomeProdotto(ordPro.getProdotto().getNomeProdotto())
					.descrizione(ordPro.getProdotto().getDescrizione())
					.prezzo(ordPro.getProdotto().getPrezzo())
					.categoria(ordPro.getProdotto().getCategoria())
					.tipoAnimale(ordPro.getProdotto().getTipoAnimale())
					.quantitaDisponibile(ordPro.getProdotto().getQuantitaDisponibile())
					.immagineUrl(ordPro.getProdotto().getImmagineUrl())
					.build();
			}
			
		
				
		return OrdineProdottoDTO.builder()
				.id(ordPro.getId())
				.ordineId(ordPro.getOrdine().getId())
				.prodotto(proDTO)
				.quantita(ordPro.getQuantita())
				.build();
		
	}

}
