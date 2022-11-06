package it.unical.mat.webapp23.catena_ristoranti.persistenza.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.PiattoDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RecensioneDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RistoranteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.UtenteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Recensione;
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
	
	@Test
	public void getAndUpdateRistorante() {
		RistoranteDao rDao = DBManager.getInstance().getRistoranteDao();
		Ristorante r = rDao.findByPrimaryKey(6L);
		r.setUbicazione("87036");
		rDao.saveOrUpdate(r);		
		
		Ristorante r2 = rDao.findByPrimaryKey(6L);
		assertEquals(r2.getUbicazione(), "87036");
	}
	
	@Test
	public void getRistorantiOfPiatti() {
		PiattoDao pDao = DBManager.getInstance().getPiattoDao();
		List<Piatto> piatti = pDao.findAll();
		for (Piatto p : piatti) {
			System.out.println("Piatto : " + p.getNome());
			for (Ristorante r : p.getRistoranti()) {
				System.out.println("  Ristorante : " + r.getNome());
			}
		}
	}
	
	@Test
	public void testNuovoPiatto() {
		Piatto newP = new Piatto();
		newP.setNome("Linguine allo scoglio");
		newP.setRistoranti(new ArrayList<Ristorante>());
		
		RistoranteDao rDao = DBManager.getInstance().getRistoranteDao();
		Ristorante rEsistente = rDao.findAll().get(0);
		newP.getRistoranti().add(rEsistente);
	
		Ristorante rNew = new Ristorante();
		rNew.setNome("La fattoria in altura");
		rNew.setUbicazione("32121");
		newP.getRistoranti().add(rNew);
		
		PiattoDao pDao = DBManager.getInstance().getPiattoDao();
		pDao.saveOrUpdate(newP);
	}
	
	@Test
	public void getRecensioni() {
		RecensioneDao rDao = DBManager.getInstance().getRecensioneDao();
		for (Recensione r : rDao.findAll()) {
			System.out.println("---");
			System.out.println("Titolo : " + r.getTitolo());
			System.out.println("Testo : " + r.getTesto());
			System.out.println("Numero mi piace : " + r.getNumeroMiPiace());
			System.out.println("Numero non mi piace : " + r.getNumeroNonMiPiace());
			System.out.println("Ristorante : " + r.getRistorante().getNome());
			System.out.println("Utente : " + r.getScrittaDa().getUsername());
		}
	}
	
	@Test
	public void testNuovaRecensione() {
		RecensioneDao rDao = DBManager.getInstance().getRecensioneDao();
		
		Recensione newR = new Recensione();
		newR.setTitolo("Non eccezionale");
		newR.setTesto("Non mi sono trovato benissimo. Qualità bassa e personale scortese");
		newR.setScrittaDa(DBManager.getInstance().getUtenteDao().findAll().get(0));
		newR.setRistorante(DBManager.getInstance().getRistoranteDao().findAll().get(0));
		rDao.saveOrUpdate(newR);
		
	}
	
	@Test
	public void deletePiatto() {
		PiattoDao pDao = DBManager.getInstance().getPiattoDao();
		Piatto p = pDao.findByPrimaryKey(2L);
		if (p != null) {
			pDao.delete(p);
		}
	}
	
	@Test
	public void piattiDiRistorante() {
		PiattoDao pDao = DBManager.getInstance().getPiattoDao();
		Ristorante r = DBManager.getInstance().getRistoranteDao()
						.findByPrimaryKey(1L);
		List<Piatto> piatti = pDao.findByRestaurantLazy(r);
		for (Piatto p : piatti) {
			System.out.println("Piatto: " + p.getNome());
			assertNull(p.ristoranti);
			List<Ristorante> ristsOfPiatto = p.getRistoranti();
			assertNotNull(p.ristoranti);
			for (Ristorante rP : ristsOfPiatto) {
				System.out.println("--- " + rP.getNome());
			}
		}
	}
}
