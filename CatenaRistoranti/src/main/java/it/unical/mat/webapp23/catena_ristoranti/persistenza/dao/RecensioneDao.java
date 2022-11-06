package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao;

import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Recensione;

public interface RecensioneDao {
	public List<Recensione> findAll();
	
	public Recensione findByPrimaryKey(Long id);
	
	public void saveOrUpdate(Recensione recensione);
	
	public void delete(Recensione recensione);
}
