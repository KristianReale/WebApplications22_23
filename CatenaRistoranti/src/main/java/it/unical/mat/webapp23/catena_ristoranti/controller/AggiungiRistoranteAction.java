package it.unical.mat.webapp23.catena_ristoranti.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.PiattoDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RistoranteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

@WebServlet("/doAddRestaurant")
public class AggiungiRistoranteAction extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PiattoDao piattoDao = DBManager.getInstance().getPiattoDao();
		
		String nome = req.getParameter("nome");
		String descrizione = req.getParameter("descrizione");
		String ubicazione = req.getParameter("ubicazione");
		String [] piatti = req.getParameterValues("piatto");
		
		Ristorante r = new Ristorante();
		r.setNome(nome);
		r.setDescrizione(descrizione);
		r.setUbicazione(ubicazione);
		
		for (String p : piatti) {
			Piatto piatto = piattoDao.findByPrimaryKey(Long.parseLong(p));
			piatto.getRistoranti().add(r);
			piattoDao.saveOrUpdate(piatto);
		}
		
		resp.sendRedirect("ristoranti");
	}	
}
