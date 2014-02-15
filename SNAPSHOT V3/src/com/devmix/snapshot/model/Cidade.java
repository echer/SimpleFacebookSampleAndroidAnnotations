/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devmix.snapshot.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Echer
 */
@DatabaseTable(tableName = "CIDADE")
public class Cidade implements Serializable {

	public static final String TABLE_NAME = "CIDADE";
	/**
	 * 
	 */
	private static final long serialVersionUID = -4572375362916664340L;

	public static final String COLUMN_ID = "CID_ID";
	@DatabaseField(columnName = COLUMN_ID, useGetSet = true, id = true)
	private Long idCidade;

	public static final String COLUMN_NOME = "CID_NOME";
	@DatabaseField(columnName = COLUMN_NOME, useGetSet = true)
	private String nomeCidade;

	public static final String FK_ESTADO = "FK_ESTADO";
	@DatabaseField(columnName = FK_ESTADO, canBeNull = false, foreign = true, useGetSet = true, foreignAutoRefresh = true)
	private Estado estado;

	public Cidade(Long idCidade, String nomeCidade, Estado estado) {
		this.idCidade = idCidade;
		this.nomeCidade = nomeCidade;
		this.estado = estado;
	}

	public Cidade() {
	}

	/**
	 * @return the idCidade
	 */
	public Long getIdCidade() {
		return idCidade;
	}

	/**
	 * @param idCidade
	 *            the idCidade to set
	 */
	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}

	/**
	 * @return the nomeCidade
	 */
	public String getNomeCidade() {
		return nomeCidade;
	}

	/**
	 * @param nomeCidade
	 *            the nomeCidade to set
	 */
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	/**
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
