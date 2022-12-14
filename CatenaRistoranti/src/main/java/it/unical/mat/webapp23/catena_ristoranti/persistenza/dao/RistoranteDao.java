package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao;

import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

public interface RistoranteDao {
	public List<Ristorante> findAll();
	
	public Ristorante findByPrimaryKey(Long id);
	
	public void saveOrUpdate(Ristorante ristorante);
	
	public void delete(Ristorante ristorante);

	public Ristorante findOneByName(String name);	
}
