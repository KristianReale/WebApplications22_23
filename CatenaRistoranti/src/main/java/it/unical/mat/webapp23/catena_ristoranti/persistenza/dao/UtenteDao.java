package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao;

import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Utente;

public interface UtenteDao {
	public List<Utente> findAll();
	
	public Utente findByPrimaryKey(String username);
	
	public void saveOrUpdate(Utente utente);
	
	public void delete(Utente utente);
}
