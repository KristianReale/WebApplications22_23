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

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.PiattoDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;


@WebServlet("/aggiungiRistorante")
public class aggiungiRistorantePage extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Piatto> piatti = 
				DBManager.getInstance().getPiattoDao().findAll();
		req.setAttribute("piatti", piatti);
		
		RequestDispatcher dispacher = req.getRequestDispatcher("views/aggiungiRistorante.html");
		dispacher.forward(req, resp);
	}
}
