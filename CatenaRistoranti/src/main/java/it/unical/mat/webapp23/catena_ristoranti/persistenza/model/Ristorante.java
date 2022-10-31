package it.unical.mat.webapp23.catena_ristoranti.persistenza.model;

public class Ristorante {
	private Long id;
	private String nome;
	private String ubicazione;
	
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
	
	
	
}
