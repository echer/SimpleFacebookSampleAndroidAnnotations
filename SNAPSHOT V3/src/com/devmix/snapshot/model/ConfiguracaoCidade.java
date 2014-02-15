package com.devmix.snapshot.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "CFG_CIDADE")
public class ConfiguracaoCidade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5973349642848397294L;

	public static final String TABLE_NAME = "CFG_CIDADE";
	
	public static final String COLUMN_ID = "CFG_CID_ID";
	@DatabaseField(columnName = COLUMN_ID, useGetSet = true, id = true)
	private long id;

	public static final String FK_CONFIGURACOES = "FK_CONFIG";
	@DatabaseField(columnName = FK_CONFIGURACOES, canBeNull = true, foreign = true, useGetSet = true)
	@JsonIgnore
	private Configuracoes configuracoes;

	public static final String FK_CIDADE = "FK_CIDADE";
	@DatabaseField(columnName = FK_CIDADE, canBeNull = false, foreign = true, useGetSet = true, foreignAutoRefresh = true)
	private Cidade cidade;

	public Configuracoes getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(Configuracoes configuracoes) {
		this.configuracoes = configuracoes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
