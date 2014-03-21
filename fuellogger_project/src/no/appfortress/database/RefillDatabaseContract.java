package no.appfortress.database;

import android.provider.BaseColumns;

/**
 * 
 * @author Morgan
 * En kontrakt klasse for tabellen "fueling".
 * 
 * Den vil holde alle verdiene som skal være i tabellen "fueling", både navn
 * og kolonner til tabellen. 
 * 
 * Tabellen skal inneholde alle påfyllingene med bensin som har blitt registrert.
 */
public final class RefillDatabaseContract {
	
	
	/**
 	 * Konstruktør som vil sørge for at hvis noen finner på å instansiere
 	 * klassen, så vil den ikke gjøre noen ting.
 	 */
	public RefillDatabaseContract(){};
	/**
	 * 
	 * @author Morgan
	 *
	 *	En klasse som holder verdiene til tabellen
	 */
	public static abstract class RefillFeedEntry implements BaseColumns{
		// Navn på tabellen
		public static final String TABLE_NAME = "fueling";
		// IDen på påfyllingen
		public static final String COLUMN_CAR_ID_FK_CAR = "car_id";
		// Datoen påfyllinga fant sted
		public static final String COLUMN_DATE = "date";
		// Hvor mange liter som ble fylt på
		public static final String COLUMN_FUEL_LITERS = "fuel_liters";
		// Hvor mye påfyllingen kostet
		public static final String COLUMN_FUEL_PRICE = "fuel_price";
		// Hvor mye det sto på odometeret til bilen når man fylte på bensin
		public static final String COLUMN_ODOMETER = "odometer";
		// Longitude på hvor påfyllingen ble registrert
		public static final String COLUMN_LONGITUDE = "longitude";
		// Latitude på hvor påfyllingen ble registrert
		public static final String COLUMN_LATITUDE = "latitude";
	}
	
}
