package it.unical.mat.webapp23.catena_ristoranti.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RistoranteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

@WebServlet("/checkRistorante")
public class CheckRistorante extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nome = req.getParameter("nome");
		RistoranteDao dao = DBManager.getInstance().getRistoranteDao();
		Ristorante rist = dao.findOneByName(nome);
		boolean trovato;
		if (rist == null) {
			trovato = false;
		}else {
			trovato = true;
		}
		resp.getWriter().println(trovato);
	}
}	
