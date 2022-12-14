package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.DBManager;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.IdBroker;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.PiattoDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.RistoranteDao;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

public class PiattoDaoPostgres implements PiattoDao{
	Connection conn;
	
	public PiattoDaoPostgres(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Piatto> findAll() {
		HashMap<Long, Piatto> idsPiatti = new HashMap<Long, Piatto>();
		List<Piatto> piatti = new ArrayList<Piatto>();
		
		String query = "select p.id as p_id, p.nome as p_nome, p.prezzo as p_prezzo, r.id as r_id, r.nome as r_nome, r.descrizione as r_descrizione, r.cap_ubicazione as r_cap_ubicazione  "
				+ " from piatto p, serve s, ristorante r "
				+ "where p.id = s.piatto and "
				+ "		 s.ristorante = r.id ";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Piatto piatto;
				if (idsPiatti.containsKey(rs.getLong("p_id"))) {
					piatto = idsPiatti.get(rs.getLong("p_id"));
				}else {
					piatto = new Piatto();
					piatto.setId(rs.getLong("p_id"));
					piatto.setNome(rs.getString("p_nome"));
					piatto.setPrezzo(rs.getBigDecimal("p_prezzo"));
					piatto.setRistoranti(new ArrayList<Ristorante>());
					idsPiatti.put(rs.getLong("p_id"), piatto);
					piatti.add(piatto);
				}
				Ristorante rist = new Ristorante();
				rist.setId(rs.getLong("r_id"));
				rist.setNome(rs.getString("r_nome"));
				rist.setDescrizione(rs.getString("r_descrizione"));
				rist.setUbicazione(rs.getString("r_cap_ubicazione"));
				piatto.getRistoranti().add(rist);
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
		String query = "select p.id as p_id, p.nome as p_nome, p.prezzo as p_prezzo, r.id as r_id, r.nome as r_nome, r.descrizione as r_descrizione, r.cap_ubicazione as r_cap_ubicazione  "
				+ " from piatto p, serve s, ristorante r "
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
					piatto.setId(rs.getLong("p_id"));
					piatto.setNome(rs.getString("p_nome"));
					piatto.setPrezzo(rs.getBigDecimal("p_prezzo"));
					piatto.setRistoranti(new ArrayList<Ristorante>());
				}
				Ristorante r = new Ristorante();
				r.setId(rs.getLong("r_id"));
				r.setNome(rs.getString("r_nome"));
				r.setDescrizione(rs.getString("r_descrizione"));
				r.setUbicazione(rs.getString("r_cap_ubicazione"));
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
		if (piatto.getId() == null) {
			String insertStr = "INSERT INTO piatto VALUES (?, ?, ?)";
			
			try {
				PreparedStatement st = conn.prepareStatement(insertStr);
				
				Long newId = IdBroker.getId(conn);
				piatto.setId(newId);
				
				st.setLong(1, newId);
				st.setString(2, piatto.getNome());
				st.setBigDecimal(3, piatto.getPrezzo());
				st.executeUpdate();
				
				RistoranteDao rDao = DBManager.getInstance().getRistoranteDao();
				for (Ristorante r : piatto.getRistoranti()) {
					rDao.saveOrUpdate(r);
					
					String serveStr = "INSERT INTO serve VALUES (?, ?, ?)";
					
					PreparedStatement stServe = conn.prepareStatement(serveStr);
					
					Long newIdServe = IdBroker.getId(conn);
					
					stServe.setLong(1, newIdServe);
					stServe.setLong(2, piatto.getId());
					stServe.setLong(3, r.getId());
					
					stServe.executeUpdate();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			String updateStr = "UPDATE piatto set nome = ?, prezzo = ? "
													+ "where id = ?";
			
			PreparedStatement st;
			try {
				st = conn.prepareStatement(updateStr);
			
				st.setString(1, piatto.getNome());
				st.setBigDecimal(2, piatto.getPrezzo());
				st.setLong(3, piatto.getId());							
				
				st.executeUpdate();
				
				RistoranteDao rDao = DBManager.getInstance().getRistoranteDao();
				for (Ristorante r : piatto.getRistoranti()) {
					rDao.saveOrUpdate(r);
					
					String checkThereIs = "SELECT * FROM serve WHERE piatto = ? and ristorante = ?";
					PreparedStatement stCheckServe = conn.prepareStatement(checkThereIs);
					stCheckServe.setLong(1, piatto.getId());
					stCheckServe.setLong(2, r.getId());
					
					ResultSet rsCheck = stCheckServe.executeQuery();
					PreparedStatement stServe;
					if (rsCheck.next()) {
						String serveStr = "UPDATE serve SET piatto = ?, ristorante = ? "
															+ "WHERE id = ?";						
						stServe = conn.prepareStatement(serveStr);
						
						stServe.setLong(1, piatto.getId());
						stServe.setLong(2, r.getId());
						stServe.setLong(3, rsCheck.getLong("id"));
					}else {
						String serveStr = "INSERT INTO serve VALUES (?, ?, ?)";
						
						stServe = conn.prepareStatement(serveStr);
						
						Long newIdServe = IdBroker.getId(conn);
						
						stServe.setLong(1, newIdServe);
						stServe.setLong(2, piatto.getId());
						stServe.setLong(3, r.getId());
					}
					
					stServe.executeUpdate();
				}
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Piatto piatto) {
		String query = "DELETE FROM piatto WHERE id = ?";		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setLong(1, piatto.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Piatto> findByRestaurantLazy(Ristorante ristorante) {
		List<Piatto> piatti = new ArrayList<Piatto>();
		
		String query = "select p.id as p_id, p.nome as p_nome, p.prezzo as p_prezzo "
				+ " from piatto p, serve s, ristorante r "
				+ "where p.id = s.piatto and "
				+ "		 s.ristorante = r.id "
				+ "and r.id = ?";
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setLong(1, ristorante.getId());
		
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Piatto piatto = new PiattoProxy(conn);
				piatto.setId(rs.getLong("p_id"));
				piatto.setNome(rs.getString("p_nome"));		
				piatto.setPrezzo(rs.getBigDecimal("p_prezzo"));
				piatti.add(piatto);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return piatti;
	}

}
