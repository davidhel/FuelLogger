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

	/**
	 * 
	 * @param carBrand 	The brand of the car to insert
	 * @param carModel	The model of the car to insert
	 * @param year	Which year the car was made
	 * @param odometer	How long the car has driven 
	 * @param fueltank	How big the fueltank is
	 * @return a boolean value, if the insertion fails, it returns false, if not, return true
	 * 
	 * Inserts a car into the database.
	 */
	public boolean insertCar(String carBrand, String carModel, int year,
			int odometer, float fueltank) {
		boolean rtnValue = true;
		
		String dbCarBrand = (carBrand != null) ? "'"+carBrand+"'" : "NULL";
		String dbCarModel = (carModel != null) ? "'"+carModel+"'" : "NULL";
		String dbYear = (year != 0) ? Integer.toString(year) : "NULL";
		String dbOdometer;
		if(odometer != 0){
			dbOdometer = Integer.toString(odometer);
		}else{
			return false;
		}
		String dbFueltank = (fueltank != 0) ? Float.toString(fueltank) : "NULL";
		try {
			db = dbHelper.getWritableDatabase();
			String query = "INSERT INTO " + CarFeedEntry.TABLE_NAME + " ("
					+ CarFeedEntry.COLUMN_CAR_BRAND + ", "
					+ CarFeedEntry.COLUMN_CAR_MODEL + ", "
					+ CarFeedEntry.COLUMN_YEAR + ", "
					+ CarFeedEntry.COLUMN_ODOMETER + ", "
					+ CarFeedEntry.COLUMN_FUELTANK + ") VALUES (" + dbCarBrand
					+ "," + dbCarModel + "," + dbYear + ","
					+ dbOdometer + ","
					+ dbFueltank + ")";
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
			Log.d("Row id" , Integer.toString(rowID));
			db.execSQL(query);
		} catch (SQLiteException ex) {
			Log.e("Error in: deleteRow in CarDBHandler class", "Could not open database for reading");
		}
	}
}
