package com.devmix.snapshot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.devmix.snapshot.model.Cidade;
import com.devmix.snapshot.model.Estado;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import com.j256.ormlite.dao.Dao;
import com.orasystems.libs.utils.dao.DataBaseActionsThrows;

@EBean
public class CidadeManager implements DataBaseActionsThrows{
	
	@OrmLiteDao(helper = SnapShotDAO.class, model = Cidade.class)
	public Dao<Cidade, Long> daoCidade = null;

	@Override
	public Object carregaDados(ResultSet arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long delete(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List iteraDadosLista(ResultSet arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object iteraDadosObject(ResultSet arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int count() throws Exception{
		return daoCidade.queryForAll().size();
	}
	
	@SuppressWarnings("rawtypes")
	public List listByEstado(Estado estado) throws Exception{
		return daoCidade.queryForEq(Cidade.FK_ESTADO, estado.getIdEstado());
	}

	@Override
	public Object load(int arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long save(Object cidade) throws Exception {
		return daoCidade.create((Cidade)cidade);
	}

	@Override
	public long saveIfNotExist(Object cidade) throws Exception {
		return daoCidade.createIfNotExists((Cidade)cidade).getIdCidade();
	}

	@Override
	public Object saveIfNotExistReturn(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object saveReturn(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(Object cidade) throws Exception {
		return daoCidade.update((Cidade)cidade);
	}

	@Override
	public Object updateReturn(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
