package com.betacom.crudeliaDeAnimal.services.interfaces;

import java.util.List;

import com.betacom.crudeliaDeAnimal.dto.VeterinarioDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.requests.VeterinarioReq;

public interface IVeterinarioServices {

     void create(VeterinarioReq req)  throws CrudeliaException;

	 void update(VeterinarioReq req) throws CrudeliaException;

	 VeterinarioDTO delete(VeterinarioReq req) throws CrudeliaException;

	List<VeterinarioDTO> listAll();

	VeterinarioDTO  findById(Integer id) throws CrudeliaException;

	List<VeterinarioDTO>  findByCap(String cap) throws CrudeliaException;

	List<VeterinarioDTO>  findByProvincia(String provincia) throws CrudeliaException;

	List<VeterinarioDTO> findByRegione(String provincia) throws CrudeliaException;

  List<VeterinarioDTO>  findByIdUtente(Integer id) throws CrudeliaException;


}

