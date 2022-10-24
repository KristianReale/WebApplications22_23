package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao;

import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

public interface RistoranteDao {
	public List<Ristorante> findAll();
	
	public Ristorante findByPrimaryKey(Long id);
	
	public void saveOrUpdate(Ristorante piatto);
	
	public void delete(Ristorante piatto);
	
}
