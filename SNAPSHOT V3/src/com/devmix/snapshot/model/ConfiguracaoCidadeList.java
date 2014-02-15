package com.devmix.snapshot.model;

import java.io.Serializable;
import java.util.List;

public class ConfiguracaoCidadeList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1241530420046600155L;

	public ConfiguracaoCidadeList(List<ConfiguracaoCidade> cidades) {
		super();
		this.cidades = cidades;
	}

	private List<ConfiguracaoCidade> cidades;

	public List<ConfiguracaoCidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<ConfiguracaoCidade> cidades) {
		this.cidades = cidades;
	}
}
