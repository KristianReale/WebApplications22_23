package it.unical.mat.webapp23.catena_ristoranti.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.webapp23.catena_ristoranti.controller.ListaRecensioni.RistorantePiatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.PiattoDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

@WebServlet("/ristoranti")
public class ListaRistoranti extends HttpServlet{
	class RistorantePiatto {
		Ristorante ristorante;
		List<Piatto> piatti;
		
		public Ristorante getRistorante() {
			return ristorante;
		}
		public void setRistorante(Ristorante ristorante) {
			this.ristorante = ristorante;
		}
		public List<Piatto> getPiatti() {
			return piatti;
		}
		public void setPiatti(List<Piatto> piatti) {
			this.piatti = piatti;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		
		req.setAttribute("piatti", piatti);
		req.setAttribute("lista_ristoranti", rPiatti);
		
		RequestDispatcher dispacher = req.getRequestDispatcher("views/ristoranti.html");
		dispacher.forward(req, resp);
	}
}
