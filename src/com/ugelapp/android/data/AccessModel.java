package com.ugelapp.android.data;

import java.util.ArrayList;

import com.ugelapp.android.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AccessModel extends SQLiteOpenHelper {
	
	private static final int version = 2;
	private SQLiteDatabase db; 
	private String tag="SqlTag";
	Context context;

	public AccessModel(Context context) {
		super(context, context.getString(R.string.ugeldb), null, version);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		
		try {			
			db.execSQL("CREATE TABLE pagos(dayp Integer,mesp Integer,yearp Integer)");			
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			Log.e("Error creating tables and debug data", e.toString());
			throw e;
		} finally {
			db.endTransaction();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void registerFechaPago(int day,int mes,int yearp){
		try{
		db = getReadableDatabase();
		
		ContentValues values = new ContentValues();		
		values.put("dayp", day);
		values.put("mesp", mes);
		values.put("yearp", yearp);		
		db.insert("pagos", null, values);
		db.close();

		}catch(SQLException e){
			Log.e(tag, e.getMessage());
		}		
	}
	
	
	public int getPagoDay(int mes, int year){
		
		int day=0;
		db = getReadableDatabase();
		Cursor c = db.rawQuery("select dayp from pagos where mesp=" + mes + " and yearp=" + year,null);
		if(c.getCount()!=0){
			c.moveToFirst();
				do{		   
					day = c.getInt(0);
				}while(c.moveToNext());
		}		
		db.close();
		c.close();
		return day;		
	}
	
	
	public void deletePagosDay(){
		db=getReadableDatabase();
		db.execSQL("delete from pagos");
		db.close();
	} 
	
	
}
