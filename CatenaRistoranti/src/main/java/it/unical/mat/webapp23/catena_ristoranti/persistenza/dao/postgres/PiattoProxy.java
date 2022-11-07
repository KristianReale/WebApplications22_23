package it.unical.mat.webapp23.catena_ristoranti.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Piatto;
import it.unical.mat.webapp23.catena_ristoranti.persistenza.model.Ristorante;

public class PiattoProxy extends Piatto{
	Connection conn;

	public PiattoProxy(Connection conn) {
		setRistoranti(null);
		this.conn = conn;
	}

	@Override
	public List<Ristorante> getRistoranti() {
		if (super.getRistoranti() == null) {
			List<Ristorante> ristoranti = new ArrayList<Ristorante>();
			String query = "select r.id as r_id, r.nome as r_nome, r.cap_ubicazione as r_cap_ubicazione  "
					+ " from piatto p, serve s, ristorante r "
					+ "where p.id = s.piatto and "
					+ "		 s.ristorante = r.id "
					+ "	and p.id = ?";

			try {
				PreparedStatement st = conn.prepareStatement(query);
				st.setLong(1, getId());

				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					Ristorante r = new Ristorante();
					r.setId(rs.getLong("r_id"));
					r.setNome(rs.getString("r_nome"));
					r.setUbicazione(rs.getString("r_cap_ubicazione"));
					ristoranti.add(r);
				}
				super.setRistoranti(ristoranti);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.getRistoranti();
	}
}
