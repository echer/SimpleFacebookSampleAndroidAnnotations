package com.devmix.snapshot.dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.devmix.snapshot.model.Cidade;
import com.devmix.snapshot.model.ConfiguracaoCidade;
import com.devmix.snapshot.model.ConfiguracaoEnterprise;
import com.devmix.snapshot.model.Configuracoes;
import com.devmix.snapshot.model.Enterprise;
import com.devmix.snapshot.model.Estado;
import com.devmix.snapshot.model.Profile;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class SnapShotDAO extends OrmLiteSqliteOpenHelper {

	public static final int dataBaseVersion = 1;
	public static final String dataBaseName = "snapshot";
	public SnapShotDAO(Context context) {
		super(context, dataBaseName, null, dataBaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource con) {
		try {
			TableUtils.createTable(con, Profile.class);
			TableUtils.createTable(con, Estado.class);
			TableUtils.createTable(con, Cidade.class);
			TableUtils.createTable(con, Enterprise.class);
			TableUtils.createTable(con, Configuracoes.class);
			TableUtils.createTable(con, ConfiguracaoCidade.class);
			TableUtils.createTable(con, ConfiguracaoEnterprise.class);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] inToByte(InputStream in){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try{
			int next = in.read();
			while (next > -1) {
			    bos.write(next);
			    next = in.read();
			}
			bos.flush();
		}catch(Exception ignored){}
		return bos.toByteArray();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

}
