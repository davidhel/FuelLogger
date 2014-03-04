package no.appfortress.database;

import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import no.appfortress.fuellogger.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CarDBHelper extends SQLiteOpenHelper{
	
	private static String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ CarFeedEntry.TABLE_NAME + " (" 
			+ CarFeedEntry._ID + " INTEGER PRIMARY KEY, "
			+ CarFeedEntry.COLUMN_CAR_BRAND + " VARCHAR(255), "
			+ CarFeedEntry.COLUMN_CAR_MODEL + " VARCHAR(255), "
			+ CarFeedEntry.COLUMN_YEAR + " INTEGER, " 
			+ CarFeedEntry.COLUMN_ODOMETER + " INTEGER, "
			+ CarFeedEntry.COLUMNT_FUELTANK + " INTEGER )";
	
	private static String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + CarFeedEntry.TABLE_NAME;
	
	public CarDBHelper(Context c){
		super(c, c.getResources().getString(R.string.databaseName), null, c.getResources().getInteger(R.integer.databaseVersion));
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
