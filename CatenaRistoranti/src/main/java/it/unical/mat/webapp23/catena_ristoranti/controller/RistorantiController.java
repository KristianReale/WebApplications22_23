package it.unical.mat.webapp23.catena_ristoranti.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.PiattoDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

//Questa classe utilizza Spring MVC al posto delle Servlet per restituire viste 
//dinamiche
@Controller
public class RistorantiController {
	//"Sostituisce la servlet 'AggiungiRistoranteServletSynchronous'"
	@PostMapping("/doAddRestaurant")
	public String addRistorante(String nome, String descrizione, 
								String ubicazione, String [] piatto) {
		
		PiattoDao piattoDao = DBManager.getInstance().getPiattoDao();
		
//		String nome = req.getParameter("nome");
//		String descrizione = req.getParameter("descrizione");
//		String ubicazione = req.getParameter("ubicazione");
//		String [] piatti = req.getParameterValues("piatto");
		
		Ristorante r = new Ristorante();
		r.setNome(nome);
		r.setDescrizione(descrizione);
		r.setUbicazione(ubicazione);
		
		
		for (String pStr : piatto) {
			Piatto p = piattoDao.findByPrimaryKey(Long.parseLong(pStr));
			p.getRistoranti().add(r);
			piattoDao.saveOrUpdate(p);
		}
		
		return "redirect:ristoranti";
		//resp.sendRedirect("ristoranti");
	}
	
	//"Sostituisce la servlet 'ListaRistoranti'"
	@GetMapping("/ristoranti")
	public String ristoranti(Model model) {
		List<RistorantePiatto> rPiatti = new ArrayList<RistorantePiatto>();
		
		List<Ristorante> ristoranti = DBManager.getInstance()
							.getRistoranteDao().findAll();
		
		PiattoDao pDao = DBManager.getInstance().getPiattoDao();
		for (Ristorante rist : ristoranti) {
			RistorantePiatto rp = new RistorantePiatto();
			List<Piatto> piatti = pDao.findByRestaurantLazy(rist);
			rp.setRistorante(rist);
			rp.setPiatti(piatti);
			
			rPiatti.add(rp);
		}
		
		List<Piatto> piatti = 
				DBManager.getInstance().getPiattoDao().findAll();
		
//		req.setAttribute("piatti", piatti);
//		req.setAttribute("lista_ristoranti", rPiatti);
		model.addAttribute("piatti", piatti);
		model.addAttribute("lista_ristoranti", rPiatti);
		
		return "ristoranti.html";
		
//		
//		RequestDispatcher dispacher = req.getRequestDispatcher("views/ristoranti.html");
//		dispacher.forward(req, resp);
	}
	
	
	
	
	
	
	
	
}
