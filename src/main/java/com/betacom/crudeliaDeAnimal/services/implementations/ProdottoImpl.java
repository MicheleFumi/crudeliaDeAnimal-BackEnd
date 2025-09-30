package com.betacom.crudeliaDeAnimal.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Prodotto;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.IProdottoRepository;
import com.betacom.crudeliaDeAnimal.requests.ProdottoReq;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IProdottoServices;
import com.betacom.crudeliaDeAnimal.utils.Roles;

@Service
public class ProdottoImpl implements IProdottoServices {

	private IProdottoRepository IProR;
	private IMessaggioServices msgS;

	public ProdottoImpl(IProdottoRepository IProR, IMessaggioServices msgS) {
		this.IProR = IProR;
		this.msgS = msgS;
	}

	@Override
	public void create(Utente user, ProdottoReq req) throws CrudeliaException {

		if (user.getRole() == Roles.ADMIN) {

			Optional<Prodotto> prodotto = IProR.findByNomeProdotto(req.getNomeProdotto());

			if (prodotto.isPresent())
				throw new CrudeliaException(msgS.getMessaggio("PRODUCT_DUPLICATE"));

			if (req.getTipoAnimale() == null)
				throw new CrudeliaException(msgS.getMessaggio("PRODUCT_MISSING_FIELDS"));

			if (req.getQuantitaDisponibile() <= 0)
				throw new CrudeliaException(msgS.getMessaggio("QUANTITY_GREATER_THAN_ZERO"));

			Prodotto pr = new Prodotto();

			pr.setNomeProdotto(req.getNomeProdotto());
			pr.setDescrizione(req.getDescrizione());
			pr.setPrezzo(req.getPrezzo());
			pr.setCategoria(req.getCategoria());
			pr.setTipoAnimale(req.getTipoAnimale());
			pr.setQuantitaDisponibile(req.getQuantitaDisponibile());
			pr.setImmagineUrl(req.getImmagineUrl());
			IProR.save(pr);

		} else {
			throw new CrudeliaException("Permesso negato: solo Admin può aggiungere prodotti");
		}

	}

	@Override
	public void update(Utente user, ProdottoReq req) throws CrudeliaException {
		
		if (user.getRole() == Roles.ADMIN) {

			Optional<Prodotto> p = IProR.findById(req.getId());

			if (p.isEmpty())
				throw new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_FOUND"));

			if (p.isPresent()) {
				if (req.getNomeProdotto() != null)
					p.get().setNomeProdotto(req.getNomeProdotto());

				if (req.getDescrizione() != null)
					p.get().setDescrizione(req.getDescrizione());

				if (req.getPrezzo() != null)
					p.get().setPrezzo(req.getPrezzo());

				if (req.getCategoria() != null)
					p.get().setCategoria(req.getCategoria());

				if (req.getTipoAnimale() != null)
					p.get().setTipoAnimale(req.getTipoAnimale());

				if (req.getQuantitaDisponibile() != null)
					p.get().setQuantitaDisponibile(req.getQuantitaDisponibile());

				if (req.getImmagineUrl() != null)
					p.get().setImmagineUrl(req.getImmagineUrl());

				IProR.save(p.get());
			}

		} else {
			throw new CrudeliaException("Permesso negato: solo Admin può modificare quantità");
		}

	}

	@Override
	public ProdottoDTO delete(Utente user, ProdottoReq req) throws CrudeliaException {

		if (user.getRole() != Roles.ADMIN) {
			throw new CrudeliaException("Permesso negato: solo Admin può cancellare prodotti");
		}

		Optional<Prodotto> p = IProR.findById(req.getId());
		if (p.isEmpty())
			throw new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_FOUND"));

		IProR.delete(p.get());

		return ProdottoDTO.builder().id(p.get().getId()).nomeProdotto(p.get().getNomeProdotto())
				.descrizione(p.get().getDescrizione()).prezzo(p.get().getPrezzo()).categoria(p.get().getCategoria())
				.tipoAnimale(p.get().getTipoAnimale()).quantitaDisponibile(p.get().getQuantitaDisponibile())
				.immagineUrl(p.get().getImmagineUrl()).build();
	}


	@Override
	public List<ProdottoDTO> listAll() throws CrudeliaException {
		List<Prodotto> lP = IProR.findAll();
		if (lP.isEmpty())
			throw new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_FOUND"));
		return lP.stream()
				.map(p -> ProdottoDTO.builder().id(p.getId()).nomeProdotto(p.getNomeProdotto())
						.descrizione(p.getDescrizione()).prezzo(p.getPrezzo()).categoria(p.getCategoria())
						.tipoAnimale(p.getTipoAnimale()).quantitaDisponibile(p.getQuantitaDisponibile())
						.immagineUrl(p.getImmagineUrl()).build())
				.toList();
	}

	@Override
	public ProdottoDTO findById(Integer id) throws CrudeliaException {
		Optional<Prodotto> p = IProR.findById(id);
		if (p.isEmpty())
			throw new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_FOUND"));
		Prodotto u = p.get();

		return ProdottoDTO.builder().id(u.getId()).nomeProdotto(u.getNomeProdotto()).descrizione(u.getDescrizione())
				.prezzo(u.getPrezzo()).categoria(u.getCategoria()).tipoAnimale(u.getTipoAnimale())
				.quantitaDisponibile(u.getQuantitaDisponibile()).immagineUrl(u.getImmagineUrl()).build();
	}

}
