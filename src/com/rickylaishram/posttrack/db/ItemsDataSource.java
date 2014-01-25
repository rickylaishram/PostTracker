package com.rickylaishram.posttrack.db;


import java.util.Arrays;
import java.util.Vector;

import com.rickylaishram.posttrack.dataclass.ItemClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ItemsDataSource {
	
	private SQLiteDatabase db;
	private DBHelper dbHelper;
	private String[] allColumns = { DBHelper.IT_ADDED, DBHelper.IT_BOOKED_AT, DBHelper.IT_BOOKED_ON,
									DBHelper.IT_COLUMN_ID, DBHelper.IT_CONFIRMED, DBHelper.IT_DELIVERED_AT,
									DBHelper.IT_DELIVERED_ON, DBHelper.IT_ID, DBHelper.IT_NAME, DBHelper.IT_NEW_EXIST};
	
	public ItemsDataSource(Context context) {
		dbHelper	= new DBHelper(context);
	}
	
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public void addItem(String id, String name, String booked_at, String booked_on, 
						String delivered_at, String delivered_on, Integer confirm) {
		ContentValues values	= new ContentValues();
		values.put(DBHelper.IT_ADDED, ((int)(System.currentTimeMillis()/1000)+""));
		values.put(DBHelper.IT_BOOKED_AT, booked_at);
		values.put(DBHelper.IT_BOOKED_ON, booked_on);
		values.put(DBHelper.IT_CONFIRMED, confirm);
		values.put(DBHelper.IT_DELIVERED_AT, delivered_at);
		values.put(DBHelper.IT_DELIVERED_ON, delivered_on);
		values.put(DBHelper.IT_ID, id);
		values.put(DBHelper.IT_NAME, name);
		values.put(DBHelper.IT_NEW_EXIST, 0);
		long insertId	= db.insert(DBHelper.TABLE_ITEMS, null, values);
	}
	
	public Vector<ItemClass> fetchAllItems() {
		Vector<ItemClass> items	= new Vector<ItemClass>();
		
		Cursor cursor = db.query(DBHelper.TABLE_ITEMS, allColumns, null, null, null, null, DBHelper.IT_ADDED + " DESC", null);
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			ItemClass item	= new ItemClass();
			item.set(
						cursor.getString(cursor.getColumnIndex(DBHelper.IT_ID)),
						cursor.getString(cursor.getColumnIndex(DBHelper.IT_NAME)),
						cursor.getString(cursor.getColumnIndex(DBHelper.IT_BOOKED_AT)),
						cursor.getString(cursor.getColumnIndex(DBHelper.IT_BOOKED_ON)),
						cursor.getString(cursor.getColumnIndex(DBHelper.IT_DELIVERED_AT)),
						cursor.getString(cursor.getColumnIndex(DBHelper.IT_DELIVERED_ON)),
						cursor.getInt(cursor.getColumnIndex(DBHelper.IT_ADDED)),
						cursor.getInt(cursor.getColumnIndex(DBHelper.IT_CONFIRMED)),
						cursor.getInt(cursor.getColumnIndex(DBHelper.IT_NEW_EXIST))
					);
			items.add(item);
			
			cursor.moveToNext();
		}
		cursor.close();
		
		return items;
	}
	
	public void updateItem(String id, String booked_at, String booked_on, 
			String delivered_at, String delivered_on, Integer confirm) {
		String[] selArgs 		= new String[]{id};
		
		ContentValues values	= new ContentValues();
		values.put(DBHelper.IT_BOOKED_AT, booked_at);
		values.put(DBHelper.IT_BOOKED_ON, booked_on);
		values.put(DBHelper.IT_CONFIRMED, confirm);
		values.put(DBHelper.IT_DELIVERED_AT, delivered_at);
		values.put(DBHelper.IT_DELIVERED_ON, delivered_on);
		values.put(DBHelper.IT_NEW_EXIST, 1);
		db.update(DBHelper.TABLE_ITEMS, values, DBHelper.IT_ID + " = ?", selArgs);
	}
	
	public void updateNewExist(String id) {
		String[] selArgs 		= new String[]{id};
		
		ContentValues values	= new ContentValues();
		values.put(DBHelper.IT_NEW_EXIST, 1);
		db.update(DBHelper.TABLE_ITEMS, values, DBHelper.IT_ID + " = ?", selArgs);
	}
	
	public void updateNoNewExist(String id) {
		String[] selArgs 		= new String[]{id};
		
		ContentValues values	= new ContentValues();
		values.put(DBHelper.IT_NEW_EXIST, 0);
		db.update(DBHelper.TABLE_ITEMS, values, DBHelper.IT_ID + " = ?", selArgs);
	}
	
	public Boolean itemExist(String id) {
		String[] selArgs 		= new String[]{id};
		
		Cursor cursor = db.query(DBHelper.TABLE_ITEMS, allColumns,
						DBHelper.IT_ID + " = ?", selArgs, null, null, 
						DBHelper.IT_ADDED + " DESC", null);
		
		Boolean exist			= (cursor.getCount() > 0);
		
		return exist;
	}
}
