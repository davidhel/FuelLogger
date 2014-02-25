package no.appfortress.database;

import no.appfortress.fuellogger.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * 
 * @author Morgan
 * 			
 * 			En kontrakt klasse for tabellen "car".
 * 
 *         Den vil holde alle verdiene som skal være i tabellen "car", både navn
 *         og kolonner til tabellen.
 * 
 *         Denne tabellen vil holde bilene til hver bruker, hver bruker kan ha
 *         flere biler, og det vil derfor være mulig å lage flere biler per
 *         bruker.
 */
public final class CarDatabaseContract {

	private static String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ CarFeedEntry.TABLE_NAME + " (" 
			+ CarFeedEntry._ID + " INTEGER PRIMARY KEY, "
			+ CarFeedEntry.COLUMN_REGNR + " VARCHAR(255), "
			+ CarFeedEntry.COLUMN_CAR_BRAND + " VARCHAR(255), "
			+ CarFeedEntry.COLUMN_CAR_MODEL + " VARCHAR(255), "
			+ CarFeedEntry.COLUMN_YEAR + " INTEGER, "
			+ CarFeedEntry.COLUMN_OWNER_FK_USER + " INTEGER NOT NULL, "
			+ "FOREIGN KEY(" + CarFeedEntry.COLUMN_OWNER_FK_USER + ") REFERENCES " 
			+ UserFeedEntry.TABLE_NAME + "(" + UserFeedEntry._ID +") )";
	
	private static String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + CarFeedEntry.TABLE_NAME;

	/**
	 * Konstruktør som vil sørge for at hvis noen finner på å instansiere
	 * klassen, så vil den ikke gjøre noen ting.
	 */
	public CarDatabaseContract() {
	};

	/**
	 * 
	 * @author Morgan
	 * 
	 *         En klasse som holder verdiene til tabellen
	 */
	public static abstract class CarFeedEntry implements BaseColumns {
		// Table name
		public static final String TABLE_NAME = "car";
		// Registrerings nr til bilen
		public static final String COLUMN_REGNR = "registreringsnr";
		// Bilens merke
		public static final String COLUMN_CAR_BRAND = "car_brand";
		// Modellen til merke
		public static final String COLUMN_CAR_MODEL = "car_modell";
		// Bilens årsmodell
		public static final String COLUMN_YEAR = "year";
		// Bilens eier, som er en fremmednøkkel til "user" databasen
		public static final String COLUMN_OWNER_FK_USER = "owner";
	}
	
	/**
	 * 
	 * @author Morgan
	 *			CarDbHelper arver SQLiteOpenHelper klassen.
	 *			For å kjøre sql setninger mot tabellen, så lager
	 *			vi bare en ny instans av CarDbHelper klassen.
	 */
	public class CarDbHelper extends SQLiteOpenHelper {
		
		public static final int DATABASE_VERSION = 1;
		
		public CarDbHelper(Context c){
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
