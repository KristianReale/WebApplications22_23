package it.unical.mat.webapp23.catena_ristoranti.persistenza.model;

import java.util.List;

public class Ristorante {
	private Long id;
	private String nome;
	private String ubicazione;
	private List<Piatto> piatti;
	
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
	public String getUbicazione() {
		return ubicazione;
	}
	
	public void setUbicazione(String ubicazione) {
		this.ubicazione = ubicazione;
	}
	public List<Piatto> getPiatti() {
		return piatti;
	}
	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}
	
	
	
	
}
