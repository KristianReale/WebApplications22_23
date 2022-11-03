package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.IdBroker;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RistoranteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

public class RistoranteDaoPostgres implements RistoranteDao{
	Connection conn;
	
	public RistoranteDaoPostgres(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Ristorante> findAll() {
		List<Ristorante> ristoranti = new ArrayList<Ristorante>();
		String query = "select * from ristorante";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Ristorante ristorante = new Ristorante();
				ristorante.setId(rs.getLong("id"));
				ristorante.setNome(rs.getString("nome"));
				ristorante.setUbicazione(rs.getString("cap_ubicazione"));
				ristoranti.add(ristorante);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ristoranti;
	}

	@Override
	public Ristorante findByPrimaryKey(Long id) {
		Ristorante ristorante = null;
		String query = "select * from ristorante where id = ?";
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setLong(1, id);
		
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				ristorante = new Ristorante();
				ristorante.setId(rs.getLong("id"));
				ristorante.setNome(rs.getString("nome"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ristorante;
	}

	@Override
	public void saveOrUpdate(Ristorante ristorante) {
		
		if (ristorante.getId() == null) {
			String insertStr = "INSERT INTO ristorante VALUES (?, ?, ?)";
			
			PreparedStatement st;
			try {
				st = conn.prepareStatement(insertStr);
				
				Long newId = IdBroker.getId(conn);
				ristorante.setId(newId);
				
				st.setLong(1, newId);
				st.setString(2, ristorante.getNome());
				st.setString(3, ristorante.getUbicazione());
				
				st.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			String updateStr = "UPDATE ristorante set nome = ?, cap_ubicazione = ? where id = ?";
			
			PreparedStatement st;
			try {
				st = conn.prepareStatement(updateStr);
			
				st.setString(1, ristorante.getNome());
				st.setString(2, ristorante.getUbicazione());
				
				st.setLong(3, ristorante.getId());
				
				
				
				st.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Ristorante ristorante) {
		// TODO Auto-generated method stub
		
	}

}
