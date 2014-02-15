package com.devmix.snapshot.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "CFG_ENTERPRISE")
public class ConfiguracaoEnterprise {
	
	public static final String TABLE_NAME = "CFG_ENTERPRISE";
	
	public static final String COLUMN_ID = "CFG_CID_ID";
	@DatabaseField(columnName = COLUMN_ID, useGetSet = true, id = true)
	private long id;

	public static final String FK_CONFIGURACOES = "FK_CONFIG";
	@DatabaseField(columnName = FK_CONFIGURACOES, canBeNull = true, foreign = true, useGetSet = true)
	@JsonIgnore
	private Configuracoes configuracoes;

	public static final String FK_ENTERPRISE = "FK_ENTERPRISE";
	@DatabaseField(columnName = FK_ENTERPRISE, canBeNull = false, foreign = true, useGetSet = true, foreignAutoRefresh = true)
	private Enterprise enterprise;

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

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
}
