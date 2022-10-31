package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.PiattoDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

public class PiattoDaoPostgres implements PiattoDao{
	Connection conn;
	
	public PiattoDaoPostgres(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Piatto> findAll() {
		List<Piatto> piatti = new ArrayList<Piatto>();
		String query = "select * from piatto";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Piatto piatto = new Piatto();
				piatto.setId(rs.getLong("id"));
				piatto.setNome(rs.getString("nome"));
				
//				piatto.setRistoranti(XXX);
				//TODO PER ESERCIZIO
				
				piatti.add(piatto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return piatti;
	}

	@Override
	public Piatto findByPrimaryKey(Long id) {
		Piatto piatto = null;
		String query = "select * from piatto p, serve s, ristorante r "
				+ "where p.id = s.piatto and "
				+ "		 s.ristorante = r.id "
				+ "	and p.id = ?";
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setLong(1, id);
		
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				if (piatto == null) {
					piatto = new Piatto();
					piatto.setId(rs.getLong("p.id"));
					piatto.setNome(rs.getString("p.nome"));
					piatto.setRistoranti(new ArrayList<Ristorante>());
				}
				Ristorante r = new Ristorante();
				r.setId(rs.getLong("r.id"));
				r.setNome(rs.getString("r.nome"));
				r.setUbicazione(rs.getString("r.cap_ubicazione"));
				piatto.getRistoranti().add(r);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return piatto;
	}

	@Override
	public void saveOrUpdate(Piatto piatto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Piatto piatto) {
		// TODO Auto-generated method stub
		
	}

}
