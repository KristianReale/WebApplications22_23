package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao;

import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;

public interface PiattoDao {
	public List<Piatto> findAll();
	
	public Piatto findByPrimaryKey();
	
	public void saveOrUpdate(Piatto piatto);
	
	public void delete(Piatto piatto);
	
}
