package com.betacom.crudeliaDeAnimal.services.interfaces;

import com.betacom.crudeliaDeAnimal.dto.SlotDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface ISlotService {
    List<SlotDTO> getSlotDisponibili(Integer vetId, LocalDate data) throws CrudeliaException;
}
