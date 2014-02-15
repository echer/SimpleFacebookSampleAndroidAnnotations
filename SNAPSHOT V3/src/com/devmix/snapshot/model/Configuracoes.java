package com.devmix.snapshot.model;

import java.io.Serializable;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "CONFIG")
public class Configuracoes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7333399057183941351L;

	public static final String TABLE_NAME = "CONFIG";

	public static final String COLUMN_ID = "CON_ID";
	@DatabaseField(columnName = COLUMN_ID, useGetSet = true, id = true)
	private long id;

	@ForeignCollectionField(eager = true)
	private Collection<ConfiguracaoCidade> cidades;

	@ForeignCollectionField(eager = true)
	private Collection<ConfiguracaoEnterprise> enterprise;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<ConfiguracaoCidade> getCidades() {
		return cidades;
	}

	public void setCidades(Collection<ConfiguracaoCidade> cidades) {
		this.cidades = cidades;
	}

	public Collection<ConfiguracaoEnterprise> getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Collection<ConfiguracaoEnterprise> enterprise) {
		this.enterprise = enterprise;
	}
}
