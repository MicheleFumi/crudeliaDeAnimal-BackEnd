package com.betacom.crudeliaDeAnimal.dto;

import com.betacom.crudeliaDeAnimal.utils.StatoVisita;
import com.betacom.crudeliaDeAnimal.utils.TipoPagamenti;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@ToString
@Builder
@Getter
@Setter
public class PrenotazioneDTO {

		private Integer id;
	    private UtenteDTO utente;
	    private AnimaleDTO animale;
	    private VeterinarioDTO veterinario;
	    private LocalDate dataVisita;
	    private LocalTime oraVisita;
	    private String motivoVisita;
	    private StatoVisita statoVisita;
	    private TipoPagamenti tipoPagamento;  // online , inSede

}
