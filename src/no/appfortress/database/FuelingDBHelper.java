package no.appfortress.database;

import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import no.appfortress.database.FuelingDatabaseContract.FuelingFeedEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FuelingDBHelper extends SQLiteOpenHelper{
	
	private static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + FuelingFeedEntry.TABLE_NAME + " ( " 
			+ FuelingFeedEntry._ID + " INTEGER PRIMARY KEY, "
			+ FuelingFeedEntry.COLUMN_CAR_ID_FK_CAR + " INTEGER NOT NULL, "
			+ FuelingFeedEntry.COLUMN_DATE + " DATETIME, "
			+ FuelingFeedEntry.COLUMN_FUEL_LITERS + " REAL NOT NULL, "
			+ FuelingFeedEntry.COLUMN_FUEL_PRICE + " REAL NOT NULL, " 
			+ FuelingFeedEntry.COLUMN_ODOMETER + " INTEGER, "
			+ FuelingFeedEntry.COLUMN_LONGITUDE + " REAL, "
			+ FuelingFeedEntry.COLUMN_LATITUDE + " REAL, "
			+ "FOREIGN KEY(" + FuelingFeedEntry.COLUMN_CAR_ID_FK_CAR + ") REFERENCES "
			+ CarFeedEntry.TABLE_NAME + "(" + CarFeedEntry._ID + "))";
	
	private static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + FuelingFeedEntry.TABLE_NAME; 
	
	public FuelingDBHelper(Context c){
		super(c, MySQLiteDatabase.databaseName, null, MySQLiteDatabase.databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	
}
