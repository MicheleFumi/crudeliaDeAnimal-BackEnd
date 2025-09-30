package com.betacom.crudeliaDeAnimal.services.implementations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.crudeliaDeAnimal.dto.OrdineDTO;
import com.betacom.crudeliaDeAnimal.dto.OrdineProdottoDTO;
import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Carrello;
import com.betacom.crudeliaDeAnimal.models.CarrelloProdotto;
import com.betacom.crudeliaDeAnimal.models.Ordine;
import com.betacom.crudeliaDeAnimal.models.OrdineProdotto;
import com.betacom.crudeliaDeAnimal.models.Prodotto;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloRepository;
import com.betacom.crudeliaDeAnimal.repositories.IOrdineProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.IOrdineRepository;
import com.betacom.crudeliaDeAnimal.repositories.IProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.requests.OrdineProdottoReq;
import com.betacom.crudeliaDeAnimal.requests.OrdineReq;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IOrdineServices;
import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Slf4j

@Service
public class OrdineImpl implements IOrdineServices {
	
	    private IOrdineRepository ordR;
        private IUtenteRepository uteR;
        private IProdottoRepository prodR;
        private IOrdineProdottoRepository orProR;
        private ICarrelloRepository carrelloRepo;
        private   ICarrelloProdottoRepository carrelloProdRepo;
        private IMessaggioServices  msgS;

  
    public OrdineImpl(IOrdineRepository ordR, IUtenteRepository uteR, IProdottoRepository prodR,
				IOrdineProdottoRepository orProR, ICarrelloRepository carrelloRepo,
				ICarrelloProdottoRepository carrelloProdRepo, IMessaggioServices msgS) {
			super();
			this.ordR = ordR;
			this.uteR = uteR;
			this.prodR = prodR;
			this.orProR = orProR;
			this.carrelloRepo = carrelloRepo;
			this.carrelloProdRepo = carrelloProdRepo;
			this.msgS = msgS;
		}

	@Override
    public OrdineDTO findById(Integer id) throws CrudeliaException {
    	
        Optional<Ordine> ordineOpt = ordR.findById(id);
        
        if (ordineOpt.isEmpty()) {
            throw new CrudeliaException(msgS.getMessaggio("ORDER_NOT_FOUND"));
        }
        
       return toOrdineDTO(ordineOpt.get());
    }
	

	@Override
	public List<OrdineDTO> findByUser(Integer idUtente) throws CrudeliaException {
		
		List<Ordine> ordini = ordR.findByUtenteId(idUtente);
		
	    log.info("Ordini trovati per utente {}: {}", idUtente, ordini.size()); // <- log

	    if (ordini.isEmpty()) {
	        throw new CrudeliaException(msgS.getMessaggio("USER_NOT_FOUND"));
	    }
	    return ordini.stream()
                .map(this::toOrdineDTO)
                .collect(Collectors.toList());
	}
	

    @Override
    @Transactional
    public void create(OrdineReq req) throws CrudeliaException {

        log.debug("Create Ordine: " + req);

        Optional<Utente> utenteOpt = uteR.findById(req.getIdUtente());
        
        if (utenteOpt.isEmpty()) {
            throw new CrudeliaException(msgS.getMessaggio("USER_NOT_FOUND"));
        }
        
        Carrello carrello = carrelloRepo.findByUtenteId(req.getIdUtente())
                .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("CART_NOT_FOUND")));

        if (carrello.getProdotti().isEmpty()) {
            throw new CrudeliaException(msgS.getMessaggio("CART_EMPTY"));
        }
        
        if (carrello.getStatoOrdine() == null || !carrello.getStatoOrdine().equals(StatoOrdine.NUOVO)) {
            throw new CrudeliaException(msgS.getMessaggio("INVALID_ORDER_STATUS"));
        }

        
        Ordine ordine = new Ordine();
        ordine.setUtente(utenteOpt.get());
        //ordine.setDataOrdine(req.getDataOrdine());
        ordine.setDataOrdine(LocalDate.now());
        ordine.setStatoOrdine(StatoOrdine.ORDINATO);
        ordine.setTotaleOrdine(BigDecimal.ZERO);
        ordine.setCarrello(carrello);
        ordR.save(ordine);
        
       
        	
        BigDecimal totaleOrdine= BigDecimal.ZERO;
        
        for (CarrelloProdotto cp : carrello.getProdotti()) {
        	
            Prodotto prodotto = cp.getProdotto();

            // Controlla disponibilità stock
            int nuovaQuantita = prodotto.getQuantitaDisponibile() - cp.getQuantitaRichieste();
            if (nuovaQuantita < 0) {
                throw new CrudeliaException(msgS.getMessaggio("PRODUCT_INVALID_STOCK") + ": " + prodotto.getNomeProdotto());
            }
            prodotto.setQuantitaDisponibile(nuovaQuantita);
            prodR.save(prodotto);

            // Crea dettaglio ordine
            OrdineProdotto op = new OrdineProdotto();
            op.setOrdine(ordine);
            op.setProdotto(prodotto);
            op.setQuantita(cp.getQuantitaRichieste());
            orProR.save(op);

            // Calcola totale ordine
            totaleOrdine = totaleOrdine.add(prodotto.getPrezzo().multiply(BigDecimal.valueOf(cp.getQuantitaRichieste())));
        }

        // 4️⃣ Aggiorna totale ordine
        ordine.setTotaleOrdine(totaleOrdine);
        ordR.save(ordine);

        // 5️⃣ Svuota carrello
		
		  carrelloProdRepo.deleteAll(carrello.getProdotti());
		  carrello.getProdotti().clear();
		  carrelloRepo.save(carrello);
		 

    }


    @Override
    public void update(OrdineReq req) throws CrudeliaException {
    	
    	log.debug("Update Ordine (quantità + stock + totale): " + req);

        // 1️⃣ Recupera l'ordine
    	
        Ordine ordine = ordR.findById(req.getId())
                .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("ORDER_NOT_FOUND")));

        if (req.getDettagliOrdine() == null || req.getDettagliOrdine().isEmpty()) {
            throw new CrudeliaException(msgS.getMessaggio("PRODUCT_LIST_EMPTY"));
        }

        BigDecimal totaleOrdine = BigDecimal.ZERO;

        for (OrdineProdottoReq opReq : req.getDettagliOrdine()) {
            if (opReq.getProdotto() == null || opReq.getProdotto().getId() == null) {
                throw new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_FOUND"));
            }

            // 2️⃣ Recupera OrdineProdotto esistente
            OrdineProdotto op = orProR.findByOrdineIdAndProdottoId(ordine.getId(), opReq.getProdotto().getId())
                    .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_IN_ORDER")));

            // 3️⃣ Controllo validità quantità
            if (opReq.getQuantita() == null || opReq.getQuantita() <= 0) {
                throw new CrudeliaException(msgS.getMessaggio("INVALID_QUANTITY"));
            }

            // 4️⃣ Aggiorna disponibilità prodotto
            int differenza = opReq.getQuantita() - op.getQuantita(); // incremento o decremento
            int nuovaDisponibilita = op.getProdotto().getQuantitaDisponibile() - differenza;

            if (nuovaDisponibilita < 0) {
                throw new CrudeliaException(msgS.getMessaggio("PRODUCT_INVALID_STOCK") + ": " + op.getProdotto().getNomeProdotto());
            }

            op.getProdotto().setQuantitaDisponibile(nuovaDisponibilita);
            prodR.save(op.getProdotto());

            // 5️⃣ Aggiorna quantità OrdineProdotto
            op.setQuantita(opReq.getQuantita());
            orProR.save(op);

            // 6️⃣ Aggiorna totale ordine
            totaleOrdine = totaleOrdine.add(op.getProdotto().getPrezzo().multiply(BigDecimal.valueOf(op.getQuantita())));
        }

        // 7️⃣ Salva totale aggiornato
        ordine.setTotaleOrdine(totaleOrdine);
        ordR.save(ordine);
    }
 

  
    

    @Override
    public OrdineDTO delete(OrdineReq req) throws CrudeliaException {

    	Optional<Ordine> ordineOpt = ordR.findById(req.getId());
    	
        if (ordineOpt.isEmpty()) {

            throw new CrudeliaException(msgS.getMessaggio("ORDER_NOT_FOUND"));
        }
        
     // Se vuoi, puoi ripristinare lo stock dei prodotti
        for (OrdineProdotto op : ordineOpt.get().getDettagliOrdine()) {
            Prodotto prodotto = op.getProdotto();
            prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile() + op.getQuantita());
            prodR.save(prodotto);
        }
        
        ordineOpt.get().setStatoOrdine(StatoOrdine.CANCELLATO);


        // Cancella tutti i dettagli ordine
       // orProR.deleteAll(ordineOpt.get().getDettagliOrdine());

        // Cancella l'ordine
       // ordR.delete(ordineOpt.get());
        
        ordR.save(ordineOpt.get());

        
		return toOrdineDTO(ordineOpt.get());
	
    	
    }

    
    @Override
    public List<OrdineDTO> listAll() {
        List<Ordine> lO = ordR.findAll();
        
        return lO.stream()
        		.map(this::toOrdineDTO)
        		.collect(Collectors.toList());
    }
    

    
	private OrdineDTO toOrdineDTO(Ordine ord) {
	
		  List<OrdineProdottoDTO> lOrdPro = ord.getDettagliOrdine().stream()
	                .map(op -> OrdineProdottoDTO.builder()
	                        .id(op.getId())
	                        .ordineId(ord.getId())
	                        .quantita(op.getQuantita())
	                        .prodotto(ProdottoDTO.builder()
	                        		.id(op.getProdotto().getId())
	                        		.nomeProdotto(op.getProdotto().getNomeProdotto())
	                        		.descrizione(op.getProdotto().getDescrizione())
	                        		.categoria(op.getProdotto().getCategoria())
	                        		.tipoAnimale(op.getProdotto().getTipoAnimale())
	                        		.prezzo(op.getProdotto().getPrezzo())
	                        		.immagineUrl(op.getProdotto().getImmagineUrl())
	                        		.quantitaDisponibile(op.getProdotto().getQuantitaDisponibile())
	                        		.build()                        		
	                        		)
	                        .build())
	                .collect(Collectors.toList());

	        return OrdineDTO.builder()
	                .id(ord.getId())
	                .idUtente(ord.getUtente().getId())
	                .dataOrdine(ord.getDataOrdine())
	                .statoOrdine(ord.getStatoOrdine().toString())
	                .totaleOrdine(ord.getTotaleOrdine())
	                .dettagliOrdine(lOrdPro)
	                .build();
	
		
	}

	

	
}
