package it.unical.mat.webapp23.catena_ristoranti.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RecensioneDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Recensione;

@RestController
@CrossOrigin("http://localhost:4200")
public class RecensioniServizi {
	@PostMapping("/dammiRecensioni")
	public List<Recensione> getRecensioni(HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		// sessionIdParam[0] = jsessionid
		// sessionIdParam[1] = DSLKGKJOWEWJHGOWJEOQPOWJEIUWHI
		//System.out.println(sessionIdParam[1]);
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Recensione> recensioni = DBManager.getInstance().getRecensioneDao().findAll();
			return recensioni;
		}
		return null;
	}
	
	@PostMapping("/deleteRecensione")
	public Boolean cancellaRecensione(HttpServletRequest req, @RequestBody HashMap<String, Long> recensione) {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			System.out.println(recensione.get("idRecensione"));
			RecensioneDao rDao = DBManager.getInstance().getRecensioneDao();
			Recensione rec = rDao.findByPrimaryKey(recensione.get("idRecensione"));
			rDao.delete(rec);
			return true;
		}
		return false;
	}
	@PostMapping("/editRecensione")
	public Boolean modificaRecensione(HttpServletRequest req, @RequestBody Recensione recensione) {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			DBManager.getInstance().getRecensioneDao().saveOrUpdate(recensione);
			System.out.println(recensione.getTesto());
			return true;
		}
		return false;
	}
	
}
