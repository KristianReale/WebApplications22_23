package it.unical.mat.webapp23.catena_ristoranti.persistenza.model;

import java.util.List;

public class Piatto {
	private Long id;
	private String nome;
	private List<Ristorante> ristoranti;
	
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
	
	
}
