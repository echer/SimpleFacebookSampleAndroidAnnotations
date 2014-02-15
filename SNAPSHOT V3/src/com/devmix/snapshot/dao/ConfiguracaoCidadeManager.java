package com.devmix.snapshot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.devmix.snapshot.model.ConfiguracaoCidade;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import com.j256.ormlite.dao.Dao;
import com.orasystems.libs.utils.dao.DataBaseActionsThrows;

@EBean
public class ConfiguracaoCidadeManager implements DataBaseActionsThrows {

	@OrmLiteDao(helper = SnapShotDAO.class, model = ConfiguracaoCidade.class)
	public Dao<ConfiguracaoCidade, Long> daoConfiguracaoCidade = null;

	@Override
	public Object carregaDados(ResultSet arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long delete(Object cc) throws Exception {
		return daoConfiguracaoCidade.delete((ConfiguracaoCidade)cc);
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

	@Override
	public Object load(int arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long save(Object cc) throws Exception {
		return daoConfiguracaoCidade.create((ConfiguracaoCidade)cc);
	}

	@Override
	public long saveIfNotExist(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
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
	public long update(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object updateReturn(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
