package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao;

import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

public interface PiattoDao {
	public List<Piatto> findAll();
	
	public Piatto findByPrimaryKey(Long id);
	
	public void saveOrUpdate(Piatto piatto);
	
	public void delete(Piatto piatto);
	
	public List<Piatto> findByRestaurantLazy(Ristorante ristorante);
	
}
