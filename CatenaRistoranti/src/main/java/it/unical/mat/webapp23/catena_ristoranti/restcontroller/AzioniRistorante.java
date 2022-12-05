package it.unical.mat.webapp23.catena_ristoranti.restcontroller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RistoranteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;
import it.unical.mat.webapp23.catena_ristoranti.utils.Status;

@RestController
public class AzioniRistorante {
	@PostMapping("/newRistorante")
	public Status aggiungiRistoranti(@RequestBody ArrayList<Ristorante> ristoranti) {
		RistoranteDao rdao = DBManager.getInstance().getRistoranteDao();
		
		for (Ristorante r: ristoranti) {
			rdao.saveOrUpdate(r);
			System.out.println(r.getNome());
		}
		return new Status("OK");
//		return "{\"message\":\"OK\"}";
	}
}
