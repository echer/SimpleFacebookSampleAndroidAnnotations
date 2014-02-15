package com.devmix.snapshot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.devmix.snapshot.model.Cidade;
import com.devmix.snapshot.model.ConfiguracaoCidade;
import com.devmix.snapshot.model.ConfiguracaoEnterprise;
import com.devmix.snapshot.model.Configuracoes;
import com.devmix.snapshot.model.Profile;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import com.j256.ormlite.dao.Dao;
import com.orasystems.libs.utils.dao.DataBaseActionsThrows;

@EBean
public class ProfileManager implements DataBaseActionsThrows{

	@OrmLiteDao(helper = SnapShotDAO.class, model = Profile.class)
	public Dao<Profile, Long> daoProfile = null;
	
	@OrmLiteDao(helper = SnapShotDAO.class, model = Configuracoes.class)
	public Dao<Configuracoes, Long> daoConfiguracoes = null;
	
	@OrmLiteDao(helper = SnapShotDAO.class, model = Cidade.class)
	public Dao<Cidade, Long> daoCidade = null;
	
	@OrmLiteDao(helper = SnapShotDAO.class, model = ConfiguracaoCidade.class)
	public Dao<ConfiguracaoCidade, Long> daoConfiguracaoCidade = null;
	
	@OrmLiteDao(helper = SnapShotDAO.class, model = ConfiguracaoEnterprise.class)
	public Dao<ConfiguracaoEnterprise, Long> daoConfiguracaoEnterprise = null;
	
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
		Profile p = (Profile)profile;
		long saved = daoProfile.create(p);
		daoConfiguracoes.create(p.getConfiguracoes());
		if(p.getConfiguracoes().getCidades() != null){
			Iterator<ConfiguracaoCidade> iteratorCidade = p.getConfiguracoes().getCidades().iterator();
			while(iteratorCidade.hasNext()){
				ConfiguracaoCidade cc = iteratorCidade.next();
				cc.setConfiguracoes(p.getConfiguracoes());
				daoCidade.createIfNotExists(cc.getCidade());
				daoConfiguracaoCidade.create(cc);
			}
		}
		if(p.getConfiguracoes().getEnterprise() != null){
			Iterator<ConfiguracaoEnterprise> iteratorEnterprise = p.getConfiguracoes().getEnterprise().iterator();
			while(iteratorEnterprise.hasNext()){
				ConfiguracaoEnterprise ce = iteratorEnterprise.next();
				ce.setConfiguracoes(p.getConfiguracoes());
				daoCidade.createIfNotExists(ce.getEnterprise().getCidade());
				daoConfiguracaoEnterprise.create(ce);
			}
		}
		return saved;
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
