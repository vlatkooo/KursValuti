package com.example.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.model.Valuta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ValutiDb {
	private SQLiteDatabase database;
	private ValutiDbOpenHelper dbHelper;
	private String[] allColumns = { ValutiDbOpenHelper.COLUMN_ID,
			ValutiDbOpenHelper.COLUMN_SHORTNAME, ValutiDbOpenHelper.COLUMN_FULLNAME,
			ValutiDbOpenHelper.COLUMN_VALUE, ValutiDbOpenHelper.COLUMN_IMAGES };

	public ValutiDb(Context context) {
		dbHelper = new ValutiDbOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
		dbHelper.close();
	}

	public boolean insert(Valuta valuta) {

		

		long insertId = database.insert(ValutiDbOpenHelper.TABLE_NAME, null,
				itemToContentValues(valuta));

		if (insertId > 0) {
			valuta.setId(insertId);
			return true;
		} else {
			return false;
		}

	}


	public List<Valuta> getAllItems() {
		List<Valuta> valuti = new ArrayList<Valuta>();

		Cursor cursor = database.query(ValutiDbOpenHelper.TABLE_NAME, allColumns,
				null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				valuti.add(cursorToItem(cursor));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return valuti;
	}

	

	protected Valuta cursorToItem(Cursor cursor) {
		Valuta item = new Valuta();
		item.setId(cursor.getLong(cursor
				.getColumnIndex(ValutiDbOpenHelper.COLUMN_ID)));

		item.setShortName(cursor.getString(cursor
				.getColumnIndex(ValutiDbOpenHelper.COLUMN_SHORTNAME)));
		
		item.setFullNameMac(cursor.getString(cursor
				.getColumnIndex(ValutiDbOpenHelper.COLUMN_FULLNAME)));
		
		item.setAverage(cursor.getString(cursor
				.getColumnIndex(ValutiDbOpenHelper.COLUMN_VALUE)));

		item.setFlag(cursor.getInt((cursor
				.getColumnIndex(ValutiDbOpenHelper.COLUMN_IMAGES))));

		return item;
	}

	protected ContentValues itemToContentValues(Valuta item) {
		ContentValues values = new ContentValues();
		if (item.getId() != null) {
			values.put(ValutiDbOpenHelper.COLUMN_ID, item.getId());
		}
		values.put(ValutiDbOpenHelper.COLUMN_SHORTNAME, item.getShortName());
		values.put(ValutiDbOpenHelper.COLUMN_FULLNAME, item.getFullNameMac());
		values.put(ValutiDbOpenHelper.COLUMN_VALUE, item.getAverage());
		values.put(ValutiDbOpenHelper.COLUMN_IMAGES, item.getFlag());
		return values;
	}

}
