package com.devmix.snapshot.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ENTERPRISE")
public class Enterprise {

	public static final String COLUMN_ID = "ENT_ID";
	@DatabaseField(columnName = COLUMN_ID, useGetSet = true, id = true)
	private long id;
	
	public static final String COLUMN_NOME = "ENT_NOME";
	@DatabaseField(columnName = COLUMN_NOME, useGetSet = true)
	private String nome;
	
	public static final String FK_CIDADE = "FK_CIDADE";
	@DatabaseField(columnName = FK_CIDADE, canBeNull = false, foreign = true, useGetSet = true, foreignAutoRefresh = true)
	private Cidade cidade;
	
	private boolean follow;
	
	private EnterpriseFoto enterpriseFoto;
	
	public static final String COLUMN_IMAGEM = "ENT_IMAGEM";
	@DatabaseField(columnName = COLUMN_IMAGEM, useGetSet = true,dataType=DataType.BYTE_ARRAY)
	private byte[] imagem;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public boolean isFollow() {
		return follow;
	}

	public void setFollow(boolean follow) {
		this.follow = follow;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public EnterpriseFoto getEnterpriseFoto() {
		return enterpriseFoto;
	}

	public void setEnterpriseFoto(EnterpriseFoto enterpriseFoto) {
		this.enterpriseFoto = enterpriseFoto;
	}
}
