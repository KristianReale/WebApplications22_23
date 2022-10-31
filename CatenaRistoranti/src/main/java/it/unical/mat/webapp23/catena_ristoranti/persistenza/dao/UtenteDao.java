package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao;

import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Utente;

public interface UtenteDao {
	public List<Utente> findAll();
	
	public Utente findByPrimaryKey(Long id);
	
	public void saveOrUpdate(Utente piatto);
	
	public void delete(Utente piatto);
}
