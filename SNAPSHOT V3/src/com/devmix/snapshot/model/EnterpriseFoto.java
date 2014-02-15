package com.devmix.snapshot.model;

import java.io.Serializable;


public class EnterpriseFoto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3278912054535273305L;

	private long id;

	private byte[] imagem;

	public EnterpriseFoto(byte[] byteArray) {
		this.imagem = byteArray;
	}
	
	public EnterpriseFoto(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}


}
