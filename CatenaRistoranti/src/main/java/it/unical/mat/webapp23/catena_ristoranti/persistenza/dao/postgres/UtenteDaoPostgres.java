package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.UtenteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Utente;

public class UtenteDaoPostgres implements UtenteDao{
	Connection conn;
	
	public UtenteDaoPostgres(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<Utente> findAll() {
		List<Utente> utenti = new ArrayList<Utente>();
		String query = "select * from utente";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Utente utente = new Utente();
				utente.setUsername(rs.getString("username"));
				utente.setPassword(rs.getString("password"));
				utente.setRuolo(rs.getString("ruolo"));
				utente.setNome(rs.getString("nome"));
				utente.setCognome(rs.getString("cognome"));
//				utente.setDataNascita(dataNascita);

				utenti.add(utente);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utenti;
	}

	@Override
	public Utente findByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(Utente piatto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Utente piatto) {
		// TODO Auto-generated method stub
		
	}

}
