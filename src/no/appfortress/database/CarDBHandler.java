package no.appfortress.database;

import java.sql.SQLException;

import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * 
 * @author Morgan
 * 
 */
public class CarDBHandler {

	private CarDBHelper dbHelper;
	private SQLiteDatabase db;

	public CarDBHandler(Context c) {
		dbHelper = new CarDBHelper(c);
	}

	/**
	 * Closes the CarDBHelper.
	 * 
	 * Call this method when you are done with this class
	 */
	public void close() {
		dbHelper.close();
	}

	public boolean insertCar(String carBrand, String carModel, int year,
			int odometer, float fueltank) {
		boolean rtnValue = true;
		try {
			db = dbHelper.getWritableDatabase();
			String query = "INSERT INTO " + CarFeedEntry.TABLE_NAME + " ("
					+ CarFeedEntry.COLUMN_CAR_BRAND + ", "
					+ CarFeedEntry.COLUMN_CAR_MODEL + ", "
					+ CarFeedEntry.COLUMN_YEAR + ", "
					+ CarFeedEntry.COLUMN_ODOMETER + ", "
					+ CarFeedEntry.COLUMN_FUELTANK + ") VALUES ('" + carBrand
					+ "','" + carModel + "'," + Integer.toString(year) + ","
					+ Integer.toString(odometer) + ","
					+ Float.toString(fueltank) + ")";
			Log.d("SQL Insert", query);
			db.execSQL(query);
		} catch (SQLiteException ex) {
			Log.e("Error in: insertCar method in CarDBHandler class",
					"Could not open database for writing.");
			ex.printStackTrace();
			return false;
		}
		db.close();
		return rtnValue;
	}

	public Cursor getAllCars() {
		Cursor c = null;
		try {
			Log.d("tyr tydosf", "1");
			db = dbHelper.getReadableDatabase();
			Log.d("tyr tydosf", "2");
			String query = "SELECT * FROM " + CarFeedEntry.TABLE_NAME;
			Log.d("tyr tydosf", query);
			c = db.rawQuery(query, null);
			Log.d("Select query", "4");
			Log.d("first boolean movetofirst",
					Boolean.toString(c.moveToFirst()));
			Log.d("first boolean isafterlaft",
					Boolean.toString(c.isAfterLast()));
			Log.d("how many rows", Integer.toString(c.getCount()));
		} catch (SQLiteException ex) {
			Log.e("Error in: getAllCars method in CarDBHandler class",
					"Could not open database for reading.");
		}
		return c;
	}

	public void deleteRow(int rowID) {
		try {
			db = dbHelper.getWritableDatabase();
			String query = "DELETE FROM " + CarFeedEntry.TABLE_NAME + " WHERE "
					+ CarFeedEntry._ID + "=" + Integer.toString(rowID);
			db.execSQL(query);
		} catch (SQLiteException ex) {
			Log.e("Error in: deleteRow in CarDBHandler class", "Could not open database for reading");
		}
	}
}
