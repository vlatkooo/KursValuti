package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ValutiDbOpenHelper extends SQLiteOpenHelper {
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SHORTNAME = "shName";
	public static final String COLUMN_FULLNAME = "fuName";
	public static final String COLUMN_VALUE = "value";
	public static final String COLUMN_IMAGES = "image";
	public static final String TABLE_NAME = "ValutiItems";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME_EXPRESSION = "ValueDatabase.db";

	private static final String DATABASE_CREATE = String
			.format("create table %s (%s  integer primary key autoincrement, "
					+ "%s text not null, %s text not null, %s text not null, %s integer);",
					TABLE_NAME, COLUMN_ID, COLUMN_SHORTNAME, COLUMN_FULLNAME, COLUMN_VALUE, COLUMN_IMAGES);

	

	public ValutiDbOpenHelper(Context context) {
		super(context,DATABASE_NAME_EXPRESSION, null,
				DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
		onCreate(db);
	}

}
