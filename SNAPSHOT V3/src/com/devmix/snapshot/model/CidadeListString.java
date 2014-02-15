package com.devmix.snapshot.model;

import java.util.ArrayList;
import java.util.List;

public class CidadeListString {
	public CidadeListString(List<String> cidades) {
		super();
		this.cidades = cidades;
	}

	private List<String> cidades = new ArrayList<String>();

	public List<String> getCidades() {
		return cidades;
	}

	public void setCidades(List<String> cidades) {
		this.cidades = cidades;
	}
}
