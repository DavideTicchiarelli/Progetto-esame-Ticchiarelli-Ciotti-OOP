package it.univpm.ProgettoEsame.models;

import java.time.LocalDate;

public class Eventi {
	private String nome;
	private String url;
	private String  citta;
	private LocalDate date;
	private String ora;
	private String genere;
	
	public Eventi(String nome, String url, String citta, LocalDate date, String ora, String genere) {
		super();
		this.nome = nome;
		this.url = url;
		this.citta = citta;
		this.date = date;
		this.ora = ora;
		this.genere = genere;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}
	
}
