package no.appfortress.database;

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

	/*
	 * public static final String COLUMN_REGNR = "registreringsnr"; // Bilens
	 * merke public static final String COLUMN_CAR_BRAND = "car_brand"; //
	 * Modellen til merke public static final String COLUMN_CAR_MODEL =
	 * "car_modell"; // Bilens årsmodell public static final String COLUMN_YEAR
	 * = "year"; }
	 */
	public boolean insertCar(String carBrand, String carModel, int year,
			int odometer, int fueltank) {
		Cursor c;
		boolean rtnValue = true;
		try {
			db = dbHelper.getWritableDatabase();
			String query = "INSERT INTO " + CarFeedEntry.TABLE_NAME + "("
					+ CarFeedEntry.COLUMN_CAR_BRAND + ","
					+ CarFeedEntry.COLUMN_CAR_MODEL + ","
					+ CarFeedEntry.COLUMN_YEAR + ","
					+ CarFeedEntry.COLUMN_ODOMETER + ","
					+ CarFeedEntry.COLUMNT_FUELTANK + ") VALUES ('" + carBrand
					+ "','" + carModel + "'," + Integer.toString(year) + ")";
			c = db.rawQuery(query, null);
		} catch (SQLiteException ex) {
			Log.e("Error in: insertCar method in CarDBHandler class",
					"Could not open database for writing.");
			return false;
		}
		if (c == null) {
			rtnValue = false;
		}
		return rtnValue;
	}

	public Cursor getAllCars() {
		Cursor c = null;
		try {
			db = dbHelper.getReadableDatabase();
			String query = "SELECT * FROM " + CarFeedEntry.TABLE_NAME;
			c = db.rawQuery(query, null);
		} catch (SQLiteException ex) {
			Log.e("Error in: getAllCars method in CarDBHandler class",
					"Could not open database for reading.");
		}

		return c;
	}
}
