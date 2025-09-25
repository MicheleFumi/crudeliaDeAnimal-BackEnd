package com.betacom.crudeliaDeAnimal.services.implementations;

import com.betacom.crudeliaDeAnimal.dto.SlotDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.repositories.IPrenotazioneRepository;
import com.betacom.crudeliaDeAnimal.services.interfaces.ISlotService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Service
@Log4j2
public class SlotImpl implements ISlotService {

    private IPrenotazioneRepository iPrenotazioneRepository;

    public SlotImpl(IPrenotazioneRepository iPrenotazioneRepository) {
        this.iPrenotazioneRepository = iPrenotazioneRepository;
    }

    @Override
    public List<SlotDTO> getSlotDisponibili(Integer vetId, LocalDate data) throws CrudeliaException {

        List<LocalTime> occupati = iPrenotazioneRepository.findOrariOccupati(vetId,data);

        LocalTime inizio = LocalTime.of(9,0);
        LocalTime fine = LocalTime.of(18,0);

        List<SlotDTO> result = new ArrayList<>();
        LocalTime current = inizio;
        log.debug("Slot controller is called for veterinario id: " + vetId + "data: " + data);
        while (!current.isAfter(fine.minusHours(1))){
            boolean disponibile = !occupati.contains(current);
            result.add(new SlotDTO(current.toString(),disponibile));
            current = current.plusHours(1);
        }

        return result;
    };
}
