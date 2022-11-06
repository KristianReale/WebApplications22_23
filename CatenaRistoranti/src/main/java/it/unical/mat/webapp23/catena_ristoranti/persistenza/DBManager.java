package it.unical.mat.webapp23.catena_ristoranti.persistenza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.PiattoDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RecensioneDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RistoranteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.UtenteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres.PiattoDaoPostgres;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres.RecensioneDaoPostgres;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres.RistoranteDaoPostgres;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres.UtenteDaoPostgres;

public class DBManager {
	private static DBManager instance = null;
	
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager() {
	}
	
	Connection conn = null;
	
	public Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	public PiattoDao getPiattoDao() {
		return new PiattoDaoPostgres(getConnection());
	}
	
	public UtenteDao getUtenteDao() {
		return new UtenteDaoPostgres(getConnection());
	}
	
	public RistoranteDao getRistoranteDao() {
		return new RistoranteDaoPostgres(getConnection());
	}
	
	public RecensioneDao getRecensioneDao() {
		return new RecensioneDaoPostgres(getConnection());
	}
}
