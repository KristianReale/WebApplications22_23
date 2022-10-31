package it.unical.mat.webapp23.catena_ristoranti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.PiattoDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RistoranteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.UtenteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres.RistoranteDaoPostgres;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Utente;

@SpringBootTest
public class DatabaseTest {
	@Test
	public void piattoDaoTest() {
		PiattoDao piattoDao = DBManager.getInstance().getPiattoDao();
		
		Piatto piattoSpaghetti = piattoDao.findByPrimaryKey(2L);
		
		System.out.println(piattoSpaghetti.getId());
		System.out.println(piattoSpaghetti.getNome());
		
		assertEquals(piattoSpaghetti.getNome(), "Spaghetti alla carbonara");
	}
	
	@Test
	public void utenteDaoTest() {
		UtenteDao utenteDao = DBManager.getInstance().getUtenteDao();
		
		List<Utente> utenti = utenteDao.findAll();
		
		for (Utente u : utenti) {
			assertNotNull(u.getUsername());
			System.out.println("Cognome " + u.getCognome());
			System.out.println("Nome " + u.getNome());
			System.out.println("Username " + u.getUsername());
		}
	}
	
	@Test
	public void creaRistorante() {
		Ristorante r = new Ristorante();
		r.setNome("Free Restaurant");
		r.setUbicazione("87100");
		
		assertNull(r.getId());
		
		RistoranteDao rDao = DBManager.getInstance().getRistoranteDao();
		rDao.saveOrUpdate(r);
		
		assertNotNull(r.getId());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
