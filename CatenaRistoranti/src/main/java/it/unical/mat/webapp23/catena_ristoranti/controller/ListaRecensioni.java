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
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;


@WebServlet("/recensioni")
public class ListaRecensioni extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Ristorante> ristoranti = DBManager.getInstance()
							.getRistoranteDao().findAll();
		req.setAttribute("lista_ristoranti", ristoranti);
		
		
		RequestDispatcher dispacher = req.getRequestDispatcher("views/recensioni/recensioni.html");
		dispacher.forward(req, resp);
	}
}
