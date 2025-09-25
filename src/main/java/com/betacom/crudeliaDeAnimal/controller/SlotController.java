package com.betacom.crudeliaDeAnimal.controller;

import com.betacom.crudeliaDeAnimal.dto.SlotDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.services.interfaces.ISlotService;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CrossOrigin("*")
@Log4j2
@RestController
@RequestMapping("rest/slot")
public class SlotController {
    private ISlotService iSlotService;

    public SlotController(ISlotService iSlotService) {
        this.iSlotService = iSlotService;
    }

    @GetMapping("/getSlot")
    public ResponseList<SlotDTO> getSlot(@RequestParam Integer veterinarioId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate data){
        ResponseList<SlotDTO> r = new ResponseList<>();
        try {
            r.setDati(iSlotService.getSlotDisponibili(veterinarioId,data));
            r.setRc(true);
        } catch (CrudeliaException e) {
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }
}
