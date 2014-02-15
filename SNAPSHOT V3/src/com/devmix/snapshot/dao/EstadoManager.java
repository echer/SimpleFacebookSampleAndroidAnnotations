package com.devmix.snapshot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import com.devmix.snapshot.model.Cidade;
import com.devmix.snapshot.model.Estado;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import com.j256.ormlite.dao.Dao;
import com.orasystems.libs.utils.dao.DataBaseActionsThrows;

@EBean
public class EstadoManager implements DataBaseActionsThrows{
	
	@OrmLiteDao(helper = SnapShotDAO.class, model = Estado.class)
	public Dao<Estado, Long> daoEstado = null;
	
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
		return daoEstado.queryForAll();
	}

	@Override
	public Object load(int arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long save(Object estado) throws Exception {
		return daoEstado.create((Estado)estado);
	}

	@Override
	public long saveIfNotExist(Object estado) throws Exception {
		return daoEstado.createIfNotExists((Estado)estado).getIdEstado();
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
	
	public void saveEstadoCidadeBatch(final List<Estado> estado) throws Exception{
		daoEstado.callBatchTasks(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				for(Estado e:estado){
					daoEstado.create(e);
					for(Cidade c:e.getCidades()){
						c.setEstado(e);
						daoCidade.create(c);
					}
				}
				return null;
			}

		});
	}

	public int count() throws Exception{
		return daoEstado.queryForAll().size();
	}

}
