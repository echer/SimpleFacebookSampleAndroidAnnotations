/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devmix.snapshot.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Echer
 */
@DatabaseTable(tableName = "ESTADO")
public class Estado implements Serializable {
	public static final String TABLE_NAME = "ESTADO";
	/**
	 * 
	 */
	private static final long serialVersionUID = 6967419235870849528L;

	public static final String COLUMN_ID = "EST_ID";
	@DatabaseField(columnName = COLUMN_ID, useGetSet = true, id = true)
	private Long idEstado;
	
	public static final String COLUMN_NOME = "EST_NOME";
	@DatabaseField(columnName = COLUMN_NOME, useGetSet = true)
	private String nomeEstado;

	public static final String COLUMN_UF = "EST_UF";
	@DatabaseField(columnName = COLUMN_UF, useGetSet = true)
	private String ufEstado;

	@JsonIgnore
	private List<Cidade> cidades;

	public Estado(Long idEstado, String nomeEstado, String ufEstado) {
		this.idEstado = idEstado;
		this.nomeEstado = nomeEstado;
		this.ufEstado = ufEstado;
	}

	public Estado() {
	}

	/**
	 * @return the idEstado
	 */
	public Long getIdEstado() {
		return idEstado;
	}

	/**
	 * @param idEstado
	 *            the idEstado to set
	 */
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * @return the nomeEstado
	 */
	public String getNomeEstado() {
		return nomeEstado;
	}

	/**
	 * @param nomeEstado
	 *            the nomeEstado to set
	 */
	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	/**
	 * @return the ufEstado
	 */
	public String getUfEstado() {
		return ufEstado;
	}

	/**
	 * @param ufEstado
	 *            the ufEstado to set
	 */
	public void setUfEstado(String ufEstado) {
		this.ufEstado = ufEstado;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
}
