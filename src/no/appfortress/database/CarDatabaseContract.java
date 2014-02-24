package no.appfortress.database;

import android.provider.BaseColumns;
/**
 * 
 * @author Morgan
 * En kontrakt klasse for tabellen "car".
 * 
 * Den vil holde alle verdiene som skal være i tabellen "car", både navn
 * og kolonner til tabellen.
 * 
 * Denne tabellen vil holde bilene til hver bruker, hver bruker kan ha flere
 * biler, og det vil derfor være mulig å lage flere biler per bruker.
 */
public class CarDatabaseContract {
	
 	/**
 	 * Konstruktør som vil sørge for at hvis noen finner på å instansiere
 	 * klassen, så vil den ikke gjøre noen ting.
 	 */
	public CarDatabaseContract(){};
	/**
	 * 
	 * @author Morgan
	 *
	 *	En klasse som holder verdiene til tabellen
	 */
	public static abstract class FeedEntry implements BaseColumns{
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
}
