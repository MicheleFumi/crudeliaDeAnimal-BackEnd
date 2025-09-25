package com.betacom.crudeliaDeAnimal.requests;


import com.betacom.crudeliaDeAnimal.utils.StatoVisita;
import com.betacom.crudeliaDeAnimal.utils.TipoPagamenti;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class PrenotazioneReq {
    private Integer id;
    private Integer idUtente;
    private Integer idAnimale;
    private Integer idVeterinario;
    private LocalDate dataVisita;
    private LocalTime oraVisita;
    private String motivoVisita;
    private StatoVisita statoVisita;
    private TipoPagamenti tipoPagamento;
}
