package no.appfortress.database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import no.appfortress.database.FuelingDatabaseContract.FuelingFeedEntry;
import no.appfortress.fuellogger.Car;
import no.appfortress.fuellogger.Fueling;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class FuelingDBHandler {

	FuelingDBHelper dbHelper;
	SQLiteDatabase db;

	public FuelingDBHandler(Context c) {
		dbHelper = new FuelingDBHelper(c);
	}

	public void close() {
		dbHelper.close();
	}

	public Fueling newFueling(Car c, float fuelLitre, float fuelPrice,
			long odometer, Calendar date, float latitude, float longitude) {
		Fueling fueling = null; 
		try {
			db = dbHelper.getWritableDatabase();
			String dateString = Integer.toString(date.get(Calendar.YEAR)) + "-"
					+ Integer.toString(date.get(Calendar.MONTH)) + "-"
					+ Integer.toString(date.get(Calendar.DAY_OF_MONTH)) + " "
					+ Integer.toString(date.get(Calendar.HOUR_OF_DAY)) + ":"
					+ Integer.toString(date.get(Calendar.MINUTE)) + ":"
					+ Integer.toString(date.get(Calendar.SECOND));
			Log.d("dato", dateString);
			String sqlQuery = "INSERT INTO " + FuelingFeedEntry.TABLE_NAME
					+ " ( " + FuelingFeedEntry.COLUMN_CAR_ID_FK_CAR + ","
					+ FuelingFeedEntry.COLUMN_FUEL_LITERS + ","
					+ FuelingFeedEntry.COLUMN_FUEL_PRICE + ","
					+ FuelingFeedEntry.COLUMN_DATE + ","
					+ FuelingFeedEntry.COLUMN_ODOMETER + ","
					+ FuelingFeedEntry.COLUMN_LATITUDE + ","
					+ FuelingFeedEntry.COLUMN_LONGITUDE + ") VALUES ("
					+ Long.toString(c.getID()) + ","
					+ Float.toString(fuelLitre) + ","
					+ Float.toString(fuelPrice) + ",'" + dateString + "',"
					+ Long.toString(odometer) + "," + Float.toString(latitude)
					+ "," + Float.toString(longitude) + ")";
			Log.d("SQL", sqlQuery);
			db.execSQL(sqlQuery);
			sqlQuery = "SELECT " + FuelingFeedEntry._ID + " FROM " +FuelingFeedEntry.TABLE_NAME;
			Cursor cursor = db.rawQuery(sqlQuery, null);
			cursor.moveToFirst();
			long id = cursor.getLong(cursor.getColumnIndex(FuelingFeedEntry._ID));			
			fueling = new Fueling(id, c, fuelLitre, fuelPrice, odometer, date,
					latitude, longitude);
		} catch (SQLiteException ex) {
			ex.printStackTrace();
			return null;
		}
		return fueling;
	}

	public Fueling newFueling(Car c, float fuelLitre, float fuelPrice,
			long odometer) {
		return this.newFueling(c, fuelLitre, fuelPrice, odometer,
				new GregorianCalendar(), Float.MIN_VALUE, Float.MIN_VALUE);
	}

	public Fueling getFueling(long _id) {
		Fueling fueling = null;
		try {
			db = dbHelper.getReadableDatabase();
			String sqlQuery = "SELECT * FROM " + FuelingFeedEntry.TABLE_NAME
					+ " WHERE " + FuelingFeedEntry._ID + "=" + _id;
			Cursor cursor = db.rawQuery(sqlQuery, null);
			long id = cursor.getLong(cursor.getColumnIndex(FuelingFeedEntry._ID));
			Car car = getCarFromFueling(cursor.getLong(cursor
					.getColumnIndex(FuelingFeedEntry.COLUMN_CAR_ID_FK_CAR)));
			float fuelLitre = cursor.getFloat(cursor
					.getColumnIndex(FuelingFeedEntry.COLUMN_FUEL_LITERS));
			float fuelPrice = cursor.getFloat(cursor
					.getColumnIndex(FuelingFeedEntry.COLUMN_FUEL_PRICE));
			long odometer = cursor.getLong(cursor
					.getColumnIndex(FuelingFeedEntry.COLUMN_ODOMETER));
			Calendar date = getDateFromString(cursor.getString(cursor.getColumnIndex(FuelingFeedEntry.COLUMN_DATE)));
			float latitude = cursor.getFloat(cursor
					.getColumnIndex(FuelingFeedEntry.COLUMN_LATITUDE));
			float longitude = cursor.getFloat(cursor
					.getColumnIndex(FuelingFeedEntry.COLUMN_LONGITUDE));
			fueling = new Fueling(id, car, fuelLitre, fuelPrice, odometer, date,
					latitude, longitude);
			cursor.close();
		} catch (SQLiteException ex) {
			ex.printStackTrace();
		}
		return fueling;
	}

	private Calendar getDateFromString(String calendarString) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dateFormat.parse(calendarString);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return dateFormat.getCalendar();
	}

	public List<Fueling> getAllFuelings() {
		List<Fueling> fuelings = new ArrayList<Fueling>();
		try {
			db = dbHelper.getReadableDatabase();
			String sqlQuery = "SELECT * FROM " + FuelingFeedEntry.TABLE_NAME;
			Log.d("SQL", sqlQuery);
			Cursor cursor = db.rawQuery(sqlQuery, null);
			Car car;
			float fuelingLitre, fuelingPrice, latitude, longitude;
			long odometer,id;
			Calendar date;
			Log.d("Number of rows",Integer.toString(cursor.getCount()));
			if(cursor != null && cursor.moveToFirst()){
				while (!cursor.isAfterLast()) {
					id = cursor.getLong(cursor.getColumnIndex(FuelingFeedEntry._ID));
					car = getCarFromFueling(cursor.getLong(cursor
							.getColumnIndex(FuelingFeedEntry.COLUMN_CAR_ID_FK_CAR)));
					fuelingLitre = cursor.getFloat(cursor.getColumnIndex(FuelingFeedEntry.COLUMN_FUEL_LITERS));
					fuelingPrice = cursor.getFloat(cursor.getColumnIndex(FuelingFeedEntry.COLUMN_FUEL_PRICE));
					odometer = cursor.getLong(cursor.getColumnIndex(FuelingFeedEntry.COLUMN_ODOMETER));
					date = getDateFromString(cursor.getString(cursor.getColumnIndex(FuelingFeedEntry.COLUMN_DATE)));
					latitude = cursor.getFloat(cursor.getColumnIndex(FuelingFeedEntry.COLUMN_LATITUDE));
					longitude = cursor.getFloat(cursor.getColumnIndex(FuelingFeedEntry.COLUMN_LONGITUDE));
					fuelings.add(new Fueling(id, car, fuelingLitre, fuelingPrice,
							odometer, date, latitude, longitude));
					cursor.moveToNext();
				}
			}
			cursor.close();
		} catch (SQLiteException ex) {
			ex.printStackTrace();
		}
		db.close();
		return fuelings;
	}
	
	public void deleteFueling(long fuelingID){
		try{
			db = dbHelper.getWritableDatabase();
			
		}
	}

	private Car getCarFromFueling(long fkID) {
		String sqlQuery = "SELECT * FROM " + CarFeedEntry.TABLE_NAME
				+ " WHERE " + CarFeedEntry._ID + "=" + Long.toString(fkID);
		Cursor cursor = db.rawQuery(sqlQuery, null);
		cursor.moveToFirst();
		String brand = cursor.getString(cursor
				.getColumnIndex(CarFeedEntry.COLUMN_CAR_BRAND));
		String model = cursor.getString(cursor
				.getColumnIndex(CarFeedEntry.COLUMN_CAR_MODEL));
		int year = cursor.getInt(cursor
				.getColumnIndex(CarFeedEntry.COLUMN_YEAR));
		long odometer = cursor.getLong(cursor
				.getColumnIndex(CarFeedEntry.COLUMN_ODOMETER));
		float fuelTank = cursor.getFloat(cursor
				.getColumnIndex(CarFeedEntry.COLUMN_FUELTANK));
		float fuel = cursor.getFloat(cursor
				.getColumnIndex(CarFeedEntry.COLUMN_FUEL));
		Car car = new Car(fkID, brand, model, year, odometer, fuelTank);
		car.setFuel(fuel);
		cursor.close();
		return car;
	}
	
}
