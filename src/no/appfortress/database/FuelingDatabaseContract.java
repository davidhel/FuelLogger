package no.appfortress.database;

import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import no.appfortress.fuellogger.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
public class FuelingDatabaseContract {
	
	private static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + FuelingFeedEntry.TABLE_NAME + " ( " 
			+ FuelingFeedEntry._ID + " INTEGER PRIMARY KEY, "
			+ FuelingFeedEntry.COLUMN_CAR_ID_FK_CAR + " INTEGER NOT NULL, "
			+ FuelingFeedEntry.COLUMN_DATE + " DATETIME, "
			+ FuelingFeedEntry.COLUMN_FUEL_LITERS + " REAL NOT NULL, "
			+ FuelingFeedEntry.COLUMN_FUEL_PRICE + " REAL NOT NULL, " 
			+ FuelingFeedEntry.COLUMN_ODOMETER + " INTEGER, "
			+ FuelingFeedEntry.COLUMN_LONGITUDE + " REAL, "
			+ FuelingFeedEntry.COLUMN_LATITUDE + " REAL, "
			+ "FOREIGN KEY(" + FuelingFeedEntry.COLUMN_CAR_ID_FK_CAR + ") REFERENCES "
			+ CarFeedEntry.TABLE_NAME + "(" + CarFeedEntry._ID + "))";
	
	private static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + FuelingFeedEntry.TABLE_NAME; 
	/**
 	 * Konstrukt�r som vil s�rge for at hvis noen finner p� � instansiere
 	 * klassen, s� vil den ikke gj�re noen ting.
 	 */
	public FuelingDatabaseContract(){};
	/**
	 * 
	 * @author Morgan
	 *
	 *	En klasse som holder verdiene til tabellen
	 */
	public static abstract class FuelingFeedEntry implements BaseColumns{
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
	
	public class FuelingDbHelper extends SQLiteOpenHelper{

		public FuelingDbHelper(Context c){
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
	
}
