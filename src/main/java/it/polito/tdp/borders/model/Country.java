package it.polito.tdp.borders.model;

import java.util.Objects;

public class Country {
	
	private int countryId;
	private String nome;
	private String abbreviazione;
	
	public Country(int countryId, String nome, String abbreviazione) {
		super();
		this.countryId = countryId;
		this.nome = nome;
		this.abbreviazione = abbreviazione;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAbbreviazione() {
		return abbreviazione;
	}

	public void setAbbreviazione(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}

	@Override
	public int hashCode() {
		return Objects.hash(countryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return countryId == other.countryId;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", nome=" + nome + ", abbreviazione=" + abbreviazione + "]";
	}
	
	
}
