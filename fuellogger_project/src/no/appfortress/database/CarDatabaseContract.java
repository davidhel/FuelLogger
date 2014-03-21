package no.appfortress.database;

import android.provider.BaseColumns;

/**
 * 
 * @author Morgan
 * 			
 * 			En kontrakt klasse for tabellen "car".
 * 
 *         Den vil holde alle verdiene som skal v�re i tabellen "car", b�de navn
 *         og kolonner til tabellen.
 * 
 *         Denne tabellen vil holde bilene til hver bruker, hver bruker kan ha
 *         flere biler, og det vil derfor v�re mulig � lage flere biler per
 *         bruker.
 */
public final class CarDatabaseContract {

	/**
	 * Konstrukt�r som vil s�rge for at hvis noen finner p� � instansiere
	 * klassen, s� vil den ikke gj�re noen ting.
	 */
	public CarDatabaseContract() {
	};

	/**
	 * 
	 * @author Morgan
	 * 
	 *         A class which hold the values of the car table
	 */
	public static abstract class CarFeedEntry implements BaseColumns {
		// Table name
		public static final String TABLE_NAME = "car";
		// The brand of the car
		public static final String COLUMN_CAR_BRAND = "car_brand";
		// The car model
		public static final String COLUMN_CAR_MODEL = "car_modell";
		// Which year the car is from
		public static final String COLUMN_YEAR = "year";
		// What is the odometer of the car
		public static final String COLUMN_ODOMETER = "odometer";
		// How big cars fuel tank is
		public static final String COLUMN_FUELTANK = "fueltank";
		// How much fuel are in the tank
		public static final String COLUMN_FUEL = "fuel";
	}
	
}
