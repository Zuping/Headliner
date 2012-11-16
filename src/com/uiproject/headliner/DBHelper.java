package com.uiproject.headliner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DB_NAME = "HEADLINER";
	private static final String TOPIC_TABLE_NAME = "TOPIC";
	private static final String NEWS_TABLE_NAME = "NEWS";
	private static final String TOPIC_TABLE_CREATE = "CREATE TABLE "
			+ TOPIC_TABLE_NAME + " (" +
			"id integer primary key autoincrement, checked INT, topic VARCHAR(50)" +
			");";
	private static final String NEWS_TABLE_CREATE = "CREATE TABLE "
			+ NEWS_TABLE_NAME + " (" +
			"id integer primary key autoincrement, " +
			"favorite CHAR, title VARCHAR(50), abstract VARCHAR(200), date VARCHAR(20), url VARCHAR(100)" +
			");";
	
	private SQLiteDatabase db; 
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase _db) {
		db = _db;
		db.execSQL(TOPIC_TABLE_CREATE);
		db.execSQL(NEWS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
    public void insertTopic(ContentValues values) {  
        SQLiteDatabase db = getWritableDatabase();  
        db.insert(TOPIC_TABLE_NAME, null, values);  
        db.close();  
    }  
	
    public void insertNews(ContentValues values) {  
        SQLiteDatabase db = getWritableDatabase();  
        db.insert(NEWS_TABLE_NAME, null, values);  
        db.close();  
    }  
    
    public Cursor queryTopic() {  
        SQLiteDatabase db = getWritableDatabase();  
        Cursor c = db.query(TOPIC_TABLE_NAME, null, null, null, null, null, null);  
        return c;  
    }
    
    public Cursor queryNews() {  
        SQLiteDatabase db = getWritableDatabase();  
        Cursor c = db.query(NEWS_TABLE_NAME, null, null, null, null, null, null);  
        return c;  
    }  
    
    public void deleteTopic() {
    	db.delete(TOPIC_TABLE_CREATE, null, null);
    }
}
