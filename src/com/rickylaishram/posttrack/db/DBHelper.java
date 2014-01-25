package com.rickylaishram.posttrack.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME		= "posttrack.db";
	public static final int DATABASE_VERSION		= 1;
	
	public static final String TABLE_ITEMS			= "items";
	public static final String IT_COLUMN_ID			= "_id";
	public static final String IT_ID				= "tracking_id";
	public static final String IT_NAME				= "name";		//name give by user for the item
	public static final String IT_ADDED				= "added_at";	//time added in sec
	public static final String IT_CONFIRMED			= "confirmed"; 	//confirm if item is found on the server
	public static final String IT_BOOKED_ON			= "booken_on";
	public static final String IT_BOOKED_AT			= "booked_at";
	public static final String IT_DELIVERED_ON		= "delivered_on";
	public static final String IT_DELIVERED_AT		= "delivered_at";
	public static final String IT_NEW_EXIST			= "new_exist";	//if new updates not seen by user exist
	
	public static final String TABLE_DETAILS		= "details";
	public static final String DET_COLUMN_ID		= "_id";
	public static final String DET_DATE				= "date";
	public static final String DET_TIME				= "time";
	public static final String DET_LOCATION			= "location";
	public static final String DET_STATUS			= "status";
	public static final String DET_ADDED			= "added_at"; //timestamp in sec
	public static final String DET_SEEN				= "seen";
	
	//DATABASE CREATION
	private static final String DB_1_CREATE		= "create table " + TABLE_ITEMS + "(" + 
													IT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
													IT_ID + " TEXT UNIQUE ON CONFLICT REPLACE, " + 
													IT_NAME + " TEXT, " + 
													IT_ADDED + " INTEGER, " +
													IT_CONFIRMED + " INTEGER, " + 
													IT_BOOKED_AT + " TEXT, " + 
													IT_BOOKED_ON + " TEXT, " + 
													IT_DELIVERED_ON + " TEXT, " + 
													IT_DELIVERED_AT + " TEXT, " + 
													IT_NEW_EXIST + " INTEGER " + 
													");";
	
	private static final String DB_2_CREATE		= "create table " + TABLE_DETAILS + "(" + 
													DET_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
													DET_DATE + " TEXT, " + 
													DET_LOCATION + " TEXT, " + 
													DET_STATUS + " TEXT, " + 
													DET_SEEN + " INTEGER, " + 
													DET_ADDED + " INTEGER, " + 
													DET_TIME + " TEXT " + 
													");";
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_1_CREATE);
		db.execSQL(DB_2_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(), "Upgrading database from version "
		        + oldVersion + " to " + newVersion
		        + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
		onCreate(db);
	}

}
