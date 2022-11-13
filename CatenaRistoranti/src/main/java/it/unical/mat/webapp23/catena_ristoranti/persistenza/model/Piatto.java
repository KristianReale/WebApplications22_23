package it.unical.mat.webapp23.catena_ristoranti.persistenza.model;

import java.math.BigDecimal;
import java.util.List;

public class Piatto {
	Long id;
	String nome;
	BigDecimal prezzo;
	List<Ristorante> ristoranti;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Ristorante> getRistoranti() {
		return ristoranti;
	}
	public void setRistoranti(List<Ristorante> ristoranti) {
		this.ristoranti = ristoranti;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
	
	
}
