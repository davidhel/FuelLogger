package no.appfortress.database;

import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import no.appfortress.database.RefillDatabaseContract.RefillFeedEntry;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RefillDBHelper extends SQLiteOpenHelper{
	
	private static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE IF NOT EXISTS " + RefillFeedEntry.TABLE_NAME + " ( " 
			+ RefillFeedEntry._ID + " INTEGER PRIMARY KEY, "
			+ RefillFeedEntry.COLUMN_CAR_ID_FK_CAR + " INTEGER NOT NULL, "
			+ RefillFeedEntry.COLUMN_DATE + " DATETIME, "
			+ RefillFeedEntry.COLUMN_FUEL_LITERS + " REAL NOT NULL, "
			+ RefillFeedEntry.COLUMN_FUEL_PRICE + " REAL NOT NULL, " 
			+ RefillFeedEntry.COLUMN_ODOMETER + " INTEGER, "
			+ RefillFeedEntry.COLUMN_LONGITUDE + " REAL, "
			+ RefillFeedEntry.COLUMN_LATITUDE + " REAL, "
			+ "FOREIGN KEY(" + RefillFeedEntry.COLUMN_CAR_ID_FK_CAR + ") REFERENCES "
			+ CarFeedEntry.TABLE_NAME + "(" + CarFeedEntry._ID + "))";
	
	private static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + RefillFeedEntry.TABLE_NAME; 
	
	public RefillDBHelper(Context c){
		super(c, MySQLiteDatabase.databaseName, null, MySQLiteDatabase.databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			db.execSQL(SQL_CREATE_ENTRIES);
		}catch(SQLException ex){
			Log.e(RefillDBHelper.class.toString(), "Failed to create SQLiteDatabase: " + SQL_CREATE_ENTRIES);
			ex.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try{
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
		}catch(SQLException ex){
			Log.e(RefillDBHelper.class.toString(), "Failed to delete SQLiteDatabase: " + SQL_DELETE_ENTRIES);
			ex.printStackTrace();
		}
	}
	
}
