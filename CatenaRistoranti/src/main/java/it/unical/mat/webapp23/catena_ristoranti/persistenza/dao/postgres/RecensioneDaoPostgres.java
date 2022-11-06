package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.IdBroker;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RecensioneDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RistoranteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.UtenteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Recensione;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Utente;

public class RecensioneDaoPostgres implements RecensioneDao{
	Connection conn;
	
	public RecensioneDaoPostgres(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<Recensione> findAll() {
		List<Recensione> recensioni = new ArrayList<Recensione>();
		String query = "select * from recensione";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Recensione recensione = new Recensione();
				recensione.setId(rs.getLong("id"));
				recensione.setTitolo(rs.getString("titolo"));
				recensione.setTesto(rs.getString("testo"));
				recensione.setNumeroMiPiace(rs.getInt("numero_mi_piace"));
				recensione.setNumeroNonMiPiace(rs.getInt("numero_non_mi_piace"));
				
				Ristorante rist = DBManager.getInstance().
								getRistoranteDao().findByPrimaryKey(rs.getLong("ristorante"));
				recensione.setRistorante(rist);
				
				Utente utente = DBManager.getInstance().
								getUtenteDao().findByPrimaryKey(rs.getString("scritta_da"));
				recensione.setScrittaDa(utente);

				recensioni.add(recensione);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recensioni;
	}

	@Override
	public Recensione findByPrimaryKey(Long id) {
		Recensione recensione = null;
		String query = "select * from recensione where id = ?";
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				recensione = new Recensione();
				recensione.setId(rs.getLong("id"));
				recensione.setTitolo(rs.getString("titolo"));
				recensione.setTesto(rs.getString("testo"));
				recensione.setNumeroMiPiace(rs.getInt("numero_mi_piace"));
				recensione.setNumeroNonMiPiace(rs.getInt("numero_non_mi_piace"));
				
				Ristorante rist = DBManager.getInstance().
								getRistoranteDao().findByPrimaryKey(rs.getLong("ristorante"));
				recensione.setRistorante(rist);
				
				Utente utente = DBManager.getInstance().
								getUtenteDao().findByPrimaryKey(rs.getString("scritta_da"));
				recensione.setScrittaDa(utente);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recensione;
	}

	@Override
	public void saveOrUpdate(Recensione recensione) {
		if (recensione.getId() == null) {
			String insertStr = "INSERT INTO recensione VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement st;
			try {
				st = conn.prepareStatement(insertStr);
				
				Long newId = IdBroker.getId(conn);
				recensione.setId(newId);
				
				st.setLong(1, newId);
				st.setString(2, recensione.getTitolo());
				st.setString(3, recensione.getTesto());
				st.setInt(4, recensione.getNumeroMiPiace());
				st.setInt(5, recensione.getNumeroNonMiPiace());
				st.setLong(6, recensione.getRistorante().getId());
				st.setString(7, recensione.getScrittaDa().getUsername());
				
				st.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			String updateStr = "UPDATE recensione set titolo = ?, "
													+ "testo = ? "
													+ "numero_mi_piace = ? "
													+ "numero_non_mi_piace = ? "
													+ "ristorante = ? "
													+ "scritta_da = ? "
													+ "where id = ?";
			
			PreparedStatement st;
			try {
				st = conn.prepareStatement(updateStr);
			
				st.setString(1, recensione.getTitolo());
				st.setString(2, recensione.getTesto());
				st.setInt(3, recensione.getNumeroMiPiace());
				st.setInt(4, recensione.getNumeroNonMiPiace());
				
				RistoranteDao rDao = DBManager.getInstance().getRistoranteDao();
				rDao.saveOrUpdate(recensione.getRistorante());				
				st.setLong(5, recensione.getRistorante().getId());
				
				UtenteDao uDao = DBManager.getInstance().getUtenteDao();
				uDao.saveOrUpdate(recensione.getScrittaDa());
				st.setString(6, recensione.getScrittaDa().getUsername());
								
				st.setLong(7, recensione.getId());								
				
				st.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Recensione recensione) {
		String query = "DELETE FROM recensione WHERE id = ?";		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setLong(1, recensione.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
