package no.appfortress.database;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import no.appfortress.fuellogger.Car;
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
	public Car getCarById(long _id){
		long id = _id;
		int odometer;
		String brand, model;
		float fuelTank, fuel;
		int year;
		Car car;
		try{
			db = dbHelper.getReadableDatabase();
			String query = "SELECT * FROM " + CarFeedEntry.TABLE_NAME + "WHERE id="+id;
			Cursor cursor = db.rawQuery(query, null);
			cursor.moveToFirst();
			
			odometer = cursor.getInt(cursor.getColumnIndex(CarFeedEntry.COLUMN_ODOMETER));
			brand = cursor.getString(cursor.getColumnIndex(CarFeedEntry.COLUMN_CAR_BRAND));
			model = cursor.getString(cursor.getColumnIndex(CarFeedEntry.COLUMN_CAR_MODEL));
			fuelTank = cursor.getFloat(cursor.getColumnIndex(CarFeedEntry.COLUMN_FUELTANK));
			fuel = cursor.getFloat(cursor.getColumnIndex(CarFeedEntry.COLUMN_FUEL));
			year = cursor.getInt(cursor.getColumnIndex(CarFeedEntry.COLUMN_YEAR));
			
			car = new Car(id, brand,model,year,odometer,fuelTank);
			car.setFuel(fuel);
		}
		catch(SQLiteException ex){
			Log.e("Error in: getAllCars method in CarDBHandler class",
					"Could not open database for reading.");
			return null;
		}
		return car;
	}

	public List<Car> getAllCars() {
		List<Car> carList = new ArrayList<Car>();
		try {
			db = dbHelper.getReadableDatabase();
			String query = "SELECT * FROM " + CarFeedEntry.TABLE_NAME;
			Cursor cursor = db.rawQuery(query, null);
			cursor.moveToFirst();
			long id; 
			int odometer;
			String brand, model;
			float fuelTank, fuel;
			int year;
			while(!cursor.isAfterLast()){
				id = cursor.getLong(cursor.getColumnIndex(CarFeedEntry._ID));
				brand = cursor.getString(cursor.getColumnIndex(CarFeedEntry.COLUMN_CAR_BRAND));
				model = cursor.getString(cursor.getColumnIndex(CarFeedEntry.COLUMN_CAR_MODEL));
				year = cursor.getInt(cursor.getColumnIndex(CarFeedEntry.COLUMN_YEAR));
				odometer = cursor.getInt(cursor.getColumnIndex(CarFeedEntry.COLUMN_ODOMETER));
				fuelTank = cursor.getFloat(cursor.getColumnIndex(CarFeedEntry.COLUMN_FUELTANK));
				fuel = cursor.getFloat(cursor.getColumnIndex(CarFeedEntry.COLUMN_FUEL));
				Car car = new Car(id, brand, model, year, odometer, fuelTank);
				car.setFuel(fuel);
				carList.add(car);
				cursor.moveToNext();
			}
		} catch (SQLiteException ex) {
			Log.e("Error in: getAllCars method in CarDBHandler class",
					"Could not open database for reading.");
		}
		return carList;
	}

	public void deleteRow(long rowID) {
		try {
			db = dbHelper.getWritableDatabase();
			String query = "DELETE FROM " + CarFeedEntry.TABLE_NAME + " WHERE "
					+ CarFeedEntry._ID + "=" + Long.toString(rowID);
			db.execSQL(query);
		} catch (SQLiteException ex) {
			Log.e("Error in: deleteRow in CarDBHandler class", "Could not open database for reading");
		}
	}
}
