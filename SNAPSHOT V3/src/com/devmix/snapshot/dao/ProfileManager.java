package com.devmix.snapshot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.j256.ormlite.dao.Dao;
import com.orasystems.libs.utils.dao.DataBaseActionsThrows;
import com.sromku.simple.fb.entities.Profile;

@EBean
public class ProfileManager implements DataBaseActionsThrows{

	@OrmLiteDao(helper = SnapShotDAO.class, model = Profile.class)
	public Dao<Profile, Long> daoProfile = null;
	@Override
	public Object carregaDados(ResultSet arg0) throws SQLException {
		return null;
	}

	@Override
	public long delete(Object arg0) throws Exception {
		return 0;
	}

	@Override
	public List<?> iteraDadosLista(ResultSet arg0) throws SQLException {
		return null;
	}

	@Override
	public Object iteraDadosObject(ResultSet arg0) throws SQLException {
		return null;
	}

	@Override
	public List<?> list() throws Exception {
		return null;
	}

	@Override
	public Object load(int arg0) throws Exception {
		return null;
	}
	
	public Profile loadFirst() throws Exception{
		return daoProfile.queryForFirst(daoProfile.queryBuilder().prepare());
	}

	@Override
	public long save(Object profile) throws Exception {
		return daoProfile.create((Profile)profile);
	}

	@Override
	public long saveIfNotExist(Object arg0) throws Exception {
		return 0;
	}

	@Override
	public Object saveIfNotExistReturn(Object arg0) throws Exception {
		return null;
	}

	@Override
	public Object saveReturn(Object profile) throws Exception {
		return null;
	}

	@Override
	public long update(Object profile) throws Exception {
		return daoProfile.update((Profile)profile);
	}

	@Override
	public Object updateReturn(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
