package no.appfortress.database;

import android.provider.BaseColumns;

/**
 * 
 * @author Morgan
 * En kontrakt klasse for tabellen "fueling".
 * 
 * Den vil holde alle verdiene som skal v�re i tabellen "fueling", b�de navn
 * og kolonner til tabellen. 
 * 
 * Tabellen skal inneholde alle p�fyllingene med bensin som har blitt registrert.
 */
public final class RefillDatabaseContract {
	
	
	/**
 	 * Konstrukt�r som vil s�rge for at hvis noen finner p� � instansiere
 	 * klassen, s� vil den ikke gj�re noen ting.
 	 */
	public RefillDatabaseContract(){};
	/**
	 * 
	 * @author Morgan
	 *
	 *	En klasse som holder verdiene til tabellen
	 */
	public static abstract class RefillFeedEntry implements BaseColumns{
		// Navn p� tabellen
		public static final String TABLE_NAME = "fueling";
		// IDen p� p�fyllingen
		public static final String COLUMN_CAR_ID_FK_CAR = "car_id";
		// Datoen p�fyllinga fant sted
		public static final String COLUMN_DATE = "date";
		// Hvor mange liter som ble fylt p�
		public static final String COLUMN_FUEL_LITERS = "fuel_liters";
		// Hvor mye p�fyllingen kostet
		public static final String COLUMN_FUEL_PRICE = "fuel_price";
		// Hvor mye det sto p� odometeret til bilen n�r man fylte p� bensin
		public static final String COLUMN_ODOMETER = "odometer";
		// Longitude p� hvor p�fyllingen ble registrert
		public static final String COLUMN_LONGITUDE = "longitude";
		// Latitude p� hvor p�fyllingen ble registrert
		public static final String COLUMN_LATITUDE = "latitude";
	}
	
}
