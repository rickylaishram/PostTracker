package com.rickylaishram.posttrack.utils;

import java.util.Vector;

import android.content.Context;

import com.rickylaishram.posttrack.dataclass.ItemClass;
import com.rickylaishram.posttrack.db.ItemsDataSource;

public final class DBUtils {
	
	public static Vector<ItemClass> getAllItems(Context ctx) {
		Vector<ItemClass> items	= new Vector<ItemClass>();
		
		ItemsDataSource source 	= new ItemsDataSource(ctx);
		source.open();
		items	= source.fetchAllItems();
		source.close();
		
		return items;
	}
	
	public static void addNewItem(Context ctx, String name, String id) {
		ItemsDataSource source 	= new ItemsDataSource(ctx);
		source.open();
		source.addItem(id, name, "", "", "", "", 0);
		source.close();
	}
	
	public static void updateItem(Context ctx, String id, String booked_at, String booked_on, 
			String delivered_at, String delivered_on, Integer confirm) {
		ItemsDataSource source 	= new ItemsDataSource(ctx);
		source.open();
		source.updateItem(id, booked_at, booked_on, delivered_at, delivered_on, confirm);
		source.close();
	}
	
	public static void updateNewExist(Context ctx, String id) {
		ItemsDataSource source	= new ItemsDataSource(ctx);
		source.open();
		source.updateNewExist(id);
		source.close();
	}
	
	public static void updateNoNewExist(Context ctx, String id) {
		ItemsDataSource source	= new ItemsDataSource(ctx);
		source.open();
		source.updateNoNewExist(id);
		source.close();
	}
	
	public static Boolean itemExist(Context ctx, String id) {
		ItemsDataSource source	= new ItemsDataSource(ctx);
		source.open();
		Boolean exist	= source.itemExist(id);
		source.close();
		
		return exist;
	}
	
}
