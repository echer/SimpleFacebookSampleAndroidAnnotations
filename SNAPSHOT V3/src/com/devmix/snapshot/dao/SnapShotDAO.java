package com.devmix.snapshot.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sromku.simple.fb.entities.Profile;

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

}
