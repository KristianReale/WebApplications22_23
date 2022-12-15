package it.unical.mat.webapp23.catena_ristoranti.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Recensione;

@RestController
@CrossOrigin("http://localhost:4200")
public class RecensioniServizi {
	@GetMapping("/dammiRecensioni")
	public List<Recensione> getRecensioni(){
		List<Recensione> recensioni = DBManager.getInstance().getRecensioneDao().findAll();
		return recensioni;
	}
}
