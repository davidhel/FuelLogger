package no.appfortress.database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import no.appfortress.database.RefillDatabaseContract.RefillFeedEntry;
import no.appfortress.fuellogger.Car;
import no.appfortress.fuellogger.Refill;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class RefillDBHandler {

	RefillDBHelper dbHelper;
	SQLiteDatabase db;

	public RefillDBHandler(Context c) {
		dbHelper = new RefillDBHelper(c);
	}

	public void close() {
		dbHelper.close();
	}

	public Refill newRefill(Car c, float fuelLitre, float fuelPrice,
			int odometer, Calendar date, float latitude, float longitude) {
		Refill refill = null; 
		try {
			db = dbHelper.getWritableDatabase();
			String dateString = Integer.toString(date.get(Calendar.YEAR)) + "-"
					+ Integer.toString(date.get(Calendar.MONTH)) + "-"
					+ Integer.toString(date.get(Calendar.DAY_OF_MONTH)) + " "
					+ Integer.toString(date.get(Calendar.HOUR_OF_DAY)) + ":"
					+ Integer.toString(date.get(Calendar.MINUTE)) + ":"
					+ Integer.toString(date.get(Calendar.SECOND));
			Log.d("dato", dateString);
			String sqlQuery = "INSERT INTO " + RefillFeedEntry.TABLE_NAME
					+ " ( " + RefillFeedEntry.COLUMN_CAR_ID_FK_CAR + ","
					+ RefillFeedEntry.COLUMN_FUEL_LITERS + ","
					+ RefillFeedEntry.COLUMN_FUEL_PRICE + ","
					+ RefillFeedEntry.COLUMN_DATE + ","
					+ RefillFeedEntry.COLUMN_ODOMETER + ","
					+ RefillFeedEntry.COLUMN_LATITUDE + ","
					+ RefillFeedEntry.COLUMN_LONGITUDE + ") VALUES ("
					+ Long.toString(c.getID()) + ","
					+ Float.toString(fuelLitre) + ","
					+ Float.toString(fuelPrice) + ",'" + dateString + "',"
					+ Long.toString(odometer) + "," + Float.toString(latitude)
					+ "," + Float.toString(longitude) + ")";
			db.execSQL(sqlQuery);
			sqlQuery = "SELECT " + RefillFeedEntry._ID + " FROM " +RefillFeedEntry.TABLE_NAME;
			Cursor cursor = db.rawQuery(sqlQuery, null);
			cursor.moveToFirst();
			long id = cursor.getLong(cursor.getColumnIndex(RefillFeedEntry._ID));			
			refill = new Refill(id, c, fuelLitre, fuelPrice, odometer, date,
					latitude, longitude);
		} catch (SQLiteException ex) {
			ex.printStackTrace();
			return null;
		}
		return refill;
	}

	public Refill newRefill(Car c, float fuelLitre, float fuelPrice,
			int odometer) {
		return this.newRefill(c, fuelLitre, fuelPrice, odometer,
				new GregorianCalendar(), Float.MIN_VALUE, Float.MIN_VALUE);
	}
	
	public Refill newRefill(Car c, float fuelLitre, float fuelPrice, int odometer, Calendar date){
		return this.newRefill(c, fuelLitre, fuelPrice, odometer, date, Float.MIN_VALUE, Float.MIN_VALUE);
	}

	public Refill getRefill(long _id) {
		Refill refill = null;
		try {
			db = dbHelper.getReadableDatabase();
			String sqlQuery = "SELECT * FROM " + RefillFeedEntry.TABLE_NAME
					+ " WHERE " + RefillFeedEntry._ID + "=" + _id;
			Cursor cursor = db.rawQuery(sqlQuery, null);
			if(cursor != null && cursor.moveToFirst()){
				long id = cursor.getLong(cursor.getColumnIndex(RefillFeedEntry._ID));
				Car car = getCarFromRefill(cursor.getLong(cursor
						.getColumnIndex(RefillFeedEntry.COLUMN_CAR_ID_FK_CAR)));
				float fuelLitre = cursor.getFloat(cursor
						.getColumnIndex(RefillFeedEntry.COLUMN_FUEL_LITERS));
				float fuelPrice = cursor.getFloat(cursor
						.getColumnIndex(RefillFeedEntry.COLUMN_FUEL_PRICE));
				int odometer = cursor.getInt(cursor
						.getColumnIndex(RefillFeedEntry.COLUMN_ODOMETER));
				Calendar date = getDateFromString(cursor.getString(cursor.getColumnIndex(RefillFeedEntry.COLUMN_DATE)));
				float latitude = cursor.getFloat(cursor
						.getColumnIndex(RefillFeedEntry.COLUMN_LATITUDE));
				float longitude = cursor.getFloat(cursor
						.getColumnIndex(RefillFeedEntry.COLUMN_LONGITUDE));
				refill = new Refill(id, car, fuelLitre, fuelPrice, odometer, date,
						latitude, longitude);
				cursor.close();
			}else{
				return null;
			}
		} catch (SQLiteException ex) {
			ex.printStackTrace();
		}
		return refill;
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
	
	public List<Refill> getRefillsFromCar(long carID){
		List<Refill> refills = new ArrayList<Refill>();
		try{
			db = dbHelper.getReadableDatabase();
			String sqlQuery = "SELECT * FROM " + RefillFeedEntry.TABLE_NAME + " WHERE " + RefillFeedEntry.COLUMN_CAR_ID_FK_CAR + "=" + carID;
			Cursor cursor = db.rawQuery(sqlQuery, null);
			Car car;
			float refillLitre, refillPrice, latitude, longitude;
			int odometer,id;
			Calendar date;
			if(cursor != null && cursor.moveToFirst()){
				while(!cursor.isAfterLast()){
					id = cursor.getInt(cursor.getColumnIndex(RefillFeedEntry._ID));
					car = getCarFromRefill(cursor.getLong(cursor
							.getColumnIndex(RefillFeedEntry.COLUMN_CAR_ID_FK_CAR)));
					refillLitre = cursor.getFloat(cursor.getColumnIndex(RefillFeedEntry.COLUMN_FUEL_LITERS));
					refillPrice = cursor.getFloat(cursor.getColumnIndex(RefillFeedEntry.COLUMN_FUEL_PRICE));
					odometer = cursor.getInt(cursor.getColumnIndex(RefillFeedEntry.COLUMN_ODOMETER));
					date = getDateFromString(cursor.getString(cursor.getColumnIndex(RefillFeedEntry.COLUMN_DATE)));
					latitude = cursor.getFloat(cursor.getColumnIndex(RefillFeedEntry.COLUMN_LATITUDE));
					longitude = cursor.getFloat(cursor.getColumnIndex(RefillFeedEntry.COLUMN_LONGITUDE));
					refills.add(new Refill(id, car, refillLitre, refillPrice,
							odometer, date, latitude, longitude));
					cursor.moveToNext();
				}
				cursor.close();
			}
		}catch(SQLiteException ex){
			ex.printStackTrace();
		}
		return refills;
	}

	public List<Refill> getAllRefills() {
		List<Refill> refills = new ArrayList<Refill>();
		try {
			db = dbHelper.getReadableDatabase();
			String sqlQuery = "SELECT * FROM " + RefillFeedEntry.TABLE_NAME;
			Log.d("SQL", sqlQuery);
			Cursor cursor = db.rawQuery(sqlQuery, null);
			Car car;
			float refillLitre, refillPrice, latitude, longitude;
			int odometer,id;
			Calendar date;
			Log.d("Number of rows",Integer.toString(cursor.getCount()));
			if(cursor != null && cursor.moveToFirst()){
				while (!cursor.isAfterLast()) {
					id = cursor.getInt(cursor.getColumnIndex(RefillFeedEntry._ID));
					car = getCarFromRefill(cursor.getLong(cursor
							.getColumnIndex(RefillFeedEntry.COLUMN_CAR_ID_FK_CAR)));
					refillLitre = cursor.getFloat(cursor.getColumnIndex(RefillFeedEntry.COLUMN_FUEL_LITERS));
					refillPrice = cursor.getFloat(cursor.getColumnIndex(RefillFeedEntry.COLUMN_FUEL_PRICE));
					odometer = cursor.getInt(cursor.getColumnIndex(RefillFeedEntry.COLUMN_ODOMETER));
					date = getDateFromString(cursor.getString(cursor.getColumnIndex(RefillFeedEntry.COLUMN_DATE)));
					latitude = cursor.getFloat(cursor.getColumnIndex(RefillFeedEntry.COLUMN_LATITUDE));
					longitude = cursor.getFloat(cursor.getColumnIndex(RefillFeedEntry.COLUMN_LONGITUDE));
					refills.add(new Refill(id, car, refillLitre, refillPrice,
							odometer, date, latitude, longitude));
					cursor.moveToNext();
				}
				cursor.close();
			}
		} catch (SQLiteException ex) {
			ex.printStackTrace();
		}
		db.close();
		return refills;
	}
	
	
	public void deleteRefill(long refillID){
		try{
			db = dbHelper.getWritableDatabase();
			String sqlQuery = "DELETE FROM " + RefillFeedEntry.TABLE_NAME
					+ " WHERE " + RefillFeedEntry._ID + "=" + refillID;
			db.execSQL(sqlQuery);
		}catch(SQLiteException ex){
			
		}
	}

	private Car getCarFromRefill(long fkID) {
		String sqlQuery = "SELECT * FROM " + CarFeedEntry.TABLE_NAME
				+ " WHERE " + CarFeedEntry._ID + "=" + Long.toString(fkID);
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if(cursor != null && cursor.moveToFirst()){
			String brand = cursor.getString(cursor
					.getColumnIndex(CarFeedEntry.COLUMN_CAR_BRAND));
			String model = cursor.getString(cursor
					.getColumnIndex(CarFeedEntry.COLUMN_CAR_MODEL));
			int year = cursor.getInt(cursor
					.getColumnIndex(CarFeedEntry.COLUMN_YEAR));
			int odometer = cursor.getInt(cursor
					.getColumnIndex(CarFeedEntry.COLUMN_ODOMETER));
			float fuelTank = cursor.getFloat(cursor
					.getColumnIndex(CarFeedEntry.COLUMN_FUELTANK));
			float fuel = cursor.getFloat(cursor
					.getColumnIndex(CarFeedEntry.COLUMN_FUEL));
			Car car = new Car(fkID, brand, model, year, odometer, fuelTank);
			car.setFuel(fuel);
			cursor.close();
			return car;
		}else{
			return null;
		}
	}
	
}
