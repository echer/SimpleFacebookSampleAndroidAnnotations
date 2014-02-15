package com.devmix.snapshot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CidadeList implements Serializable{

	public CidadeList(List<Cidade> cidades) {
		super();
		this.cidades = cidades;
	}
	
	public CidadeList(){}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2062011951058286986L;
	private List<Cidade> cidades = new ArrayList<Cidade>();

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
}
