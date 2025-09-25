package com.betacom.crudeliaDeAnimal.services.implementations;

import com.betacom.crudeliaDeAnimal.dto.AnimaleDTO;
import com.betacom.crudeliaDeAnimal.dto.PrenotazioneDTO;

import com.betacom.crudeliaDeAnimal.dto.UtenteDTO;
import com.betacom.crudeliaDeAnimal.dto.VeterinarioDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Animale;
import com.betacom.crudeliaDeAnimal.models.Prenotazione;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.models.Veterinario;
import com.betacom.crudeliaDeAnimal.repositories.IAnimaleRepository;
import com.betacom.crudeliaDeAnimal.repositories.IPrenotazioneRepository;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.repositories.IVeterinarioRepository;
import com.betacom.crudeliaDeAnimal.requests.PrenotazioneReq;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IPrenotazioneServices;
import com.betacom.crudeliaDeAnimal.utils.StatoVisita;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrenotazioneImpl implements IPrenotazioneServices {

    public IPrenotazioneRepository IPreR;
    public IAnimaleRepository IaniR;
    public IUtenteRepository IUteR;
    public IVeterinarioRepository IVetR;
	private IMessaggioServices  msgS;



    public PrenotazioneImpl(IPrenotazioneRepository IPreR, IAnimaleRepository IaniR,
    		IUtenteRepository IUteR, IVeterinarioRepository IVetR ,IMessaggioServices  msgS) {
        this.IPreR = IPreR;
        this.IaniR = IaniR;
        this.IUteR = IUteR;
        this.IVetR = IVetR;
        this.msgS=  msgS;
    }





@Override
    public List<PrenotazioneDTO> listAll() {
        List<Prenotazione> lP = IPreR.findAll();
        return lP.stream()
                .map(r -> PrenotazioneDTO.builder()
                        .id(r.getId())
                        .dataVisita(r.getDataVisita())
                        .oraVisita(r.getOraVisita())
                        .motivoVisita(r.getMotivoVisita())
                        .statoVisita(r.getStatoVisita())
                        .tipoPagamento(r.getTipoPagamento())
                        .utente(UtenteDTO.builder()
                                .id(r.getUtente().getId())
                                .cognome(r.getUtente().getCognome())
                                .nome(r.getUtente().getNome())
                                .codiceFiscale(r.getUtente().getCodiceFiscale())
                                .telefono(r.getUtente().getTelefono())
                                .email(r.getUtente().getEmail())
                                .indirizzo(r.getUtente().getIndirizzo())
                                .password(r.getUtente().getPassword())
                                .dataRegistrazione(r.getUtente().getDataRegistrazione())
                                .role(r.getUtente().getRole())
                                .build()
                        )
                        .animale(AnimaleDTO.builder()
                                .id(r.getAnimale().getId())
                                .nomeAnimale(r.getAnimale().getNomeAnimale())
                                .tipo(r.getAnimale().getTipo())
                                .razza(r.getAnimale().getRazza())
                                .noteMediche(r.getAnimale().getNoteMediche())
                                .build()
                        )
                        .veterinario(VeterinarioDTO.builder()
                                .id(r.getVeterinario().getId())
                                .nome(r.getVeterinario().getNome())
                                .tipostruttura(r.getVeterinario().getTipostrutture())
                                .indirizzo(r.getVeterinario().getIndirizzo())
                                .telefono(r.getVeterinario().getTelefono())
                                .email(r.getVeterinario().getEmail())
                                .orariApertura(r.getVeterinario().getOrariApertura())
                                .serviziVO(r.getVeterinario().getServiziVO())
                                .build())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public void create(PrenotazioneReq req) throws CrudeliaException {
        Prenotazione pr = new Prenotazione();

        Optional<Animale> ani = IaniR.findById(req.getIdAnimale());
        if (ani.isEmpty()) {
   			throw new CrudeliaException(msgS.getMessaggio("ANIMAL_NOT_FOUND"));
        }

        Optional<Utente> ut = IUteR.findById(req.getIdUtente());
        if (ut.isEmpty()) {
   			throw new CrudeliaException(msgS.getMessaggio("USER_NOT_FOUND"));
        }

        Optional<Veterinario> vt = IVetR.findById(req.getIdVeterinario());
        if (vt.isEmpty()) {
   			throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));
        }
        pr.setUtente(ut.get());
        pr.setAnimale(ani.get());
        pr.setVeterinario(vt.get());
        pr.setDataVisita(req.getDataVisita());
        pr.setOraVisita(req.getOraVisita());
        pr.setMotivoVisita(req.getMotivoVisita());
       // pr.setStatoVisita(StatoVisita.IN_LAVORAZIONE);
        pr.setStatoVisita(StatoVisita.valueOf(req.getStatoVisita().toString()));
        pr.setTipoPagamento(req.getTipoPagamento());

        IPreR.save(pr);
    }


    @Override
    public void update(PrenotazioneReq req) throws CrudeliaException {

            Prenotazione pr = IPreR.findById(req.getId())
                    .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE")));


            // Aggiorno solo i campi consentiti
            Optional<Animale> ani = IaniR.findById(req.getIdAnimale());
            if (ani.isEmpty()) {
       			throw new CrudeliaException(msgS.getMessaggio("ANIMAL_NOT_FOUND"));
            }
            pr.setAnimale(ani.get());

            Optional<Veterinario> vt = IVetR.findById(req.getIdVeterinario());
            if (vt.isEmpty()) {
       			throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));
            }
            pr.setVeterinario(vt.get());
            pr.setDataVisita(req.getDataVisita());
            pr.setOraVisita(req.getOraVisita());
            pr.setMotivoVisita(req.getMotivoVisita());
            pr.setStatoVisita(req.getStatoVisita());   // qui lo puoi prendere dal req, non forzarlo a PRENOTATA
            pr.setTipoPagamento(req.getTipoPagamento());

            IPreR.save(pr);

    }

    @Override
    public PrenotazioneDTO delete(PrenotazioneReq req) throws CrudeliaException {
        Optional<Prenotazione> pr= IPreR.findById(req.getId());
        if(pr.isEmpty())
   			throw new CrudeliaException(msgS.getMessaggio("VISIT_NOT_FOUND"));
        return PrenotazioneDTO.builder()
            .id(pr.get().getId())
            .dataVisita(pr.get().getDataVisita())
            .oraVisita(pr.get().getOraVisita())
            .motivoVisita(pr.get().getMotivoVisita())
            .statoVisita(pr.get().getStatoVisita())
            .tipoPagamento(pr.get().getTipoPagamento())
            .utente(UtenteDTO.builder()
                    .id(pr.get().getUtente().getId())
                    .cognome(pr.get().getUtente().getCognome())
                    .nome(pr.get().getUtente().getNome())
                    .codiceFiscale(pr.get().getUtente().getCodiceFiscale())
                    .telefono(pr.get().getUtente().getTelefono())
                    .email(pr.get().getUtente().getEmail())
                    .indirizzo(pr.get().getUtente().getIndirizzo())
                    .password(pr.get().getUtente().getPassword())
                    .dataRegistrazione(pr.get().getUtente().getDataRegistrazione())
                    .role(pr.get().getUtente().getRole())
                    .build()
            )
            .animale(AnimaleDTO.builder()
                    .id(pr.get().getAnimale().getId())
                    .nomeAnimale(pr.get().getAnimale().getNomeAnimale())
                    .tipo(pr.get().getAnimale().getTipo())
                    .razza(pr.get().getAnimale().getRazza())
                    .noteMediche(pr.get().getAnimale().getNoteMediche())
                    .build()
            )
            .veterinario(VeterinarioDTO.builder()
                    .id(pr.get().getVeterinario().getId())
                    .nome(pr.get().getVeterinario().getNome())
                    .tipostruttura(pr.get().getVeterinario().getTipostrutture())
                    .indirizzo(pr.get().getVeterinario().getIndirizzo())
                    .telefono(pr.get().getVeterinario().getTelefono())
                    .email(pr.get().getVeterinario().getEmail())
                    .orariApertura(pr.get().getVeterinario().getOrariApertura())
                    .serviziVO(pr.get().getVeterinario().getServiziVO())
                    .build())
            .build()
            ;
    }
    @Override
    public PrenotazioneDTO findById(Integer id) throws CrudeliaException {
        Optional<Prenotazione> p =IPreR.findById(id);
        if(p.isEmpty())
   			throw new CrudeliaException(msgS.getMessaggio("VISIT_NOT_FOUND"));
        Prenotazione pr = p.get();
        return PrenotazioneDTO.builder()
                .id(pr.getId())
                .dataVisita(pr.getDataVisita())
                .oraVisita(pr.getOraVisita())
                .motivoVisita(pr.getMotivoVisita())
                .statoVisita(pr.getStatoVisita())
                .tipoPagamento(pr.getTipoPagamento())
                .utente(UtenteDTO.builder()
                        .id(pr.getUtente().getId())
                        .cognome(pr.getUtente().getCognome())
                        .nome(pr.getUtente().getNome())
                        .codiceFiscale(pr.getUtente().getCodiceFiscale())
                        .telefono(pr.getUtente().getTelefono())
                        .email(pr.getUtente().getEmail())
                        .indirizzo(pr.getUtente().getIndirizzo())
                        .password(pr.getUtente().getPassword())
                        .dataRegistrazione(pr.getUtente().getDataRegistrazione())
                        .role(pr.getUtente().getRole())
                        .build()
                )
                .animale(AnimaleDTO.builder()
                        .id(pr.getAnimale().getId())
                        .nomeAnimale(pr.getAnimale().getNomeAnimale())
                        .tipo(pr.getAnimale().getTipo())
                        .razza(pr.getAnimale().getRazza())
                        .noteMediche(pr.getAnimale().getNoteMediche())
                        .build()
                )
                .veterinario(VeterinarioDTO.builder()
                        .id(pr.getVeterinario().getId())
                        .nome(pr.getVeterinario().getNome())
                        .tipostruttura(pr.getVeterinario().getTipostrutture())
                        .indirizzo(pr.getVeterinario().getIndirizzo())
                        .telefono(pr.getVeterinario().getTelefono())
                        .email(pr.getVeterinario().getEmail())
                        .orariApertura(pr.getVeterinario().getOrariApertura())
                        .serviziVO(pr.getVeterinario().getServiziVO())
                        .build())
                .build()
                ;
    }
}
