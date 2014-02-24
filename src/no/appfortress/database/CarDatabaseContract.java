package no.appfortress.database;

import android.provider.BaseColumns;
/**
 * 
 * @author Morgan
 * En kontrakt klasse for tabellen "car".
 * 
 * Den vil holde alle verdiene som skal v�re i tabellen "car", b�de navn
 * og kolonner til tabellen.
 * 
 * Denne tabellen vil holde bilene til hver bruker, hver bruker kan ha flere
 * biler, og det vil derfor v�re mulig � lage flere biler per bruker.
 */
public class CarDatabaseContract {
	
 	/**
 	 * Konstrukt�r som vil s�rge for at hvis noen finner p� � instansiere
 	 * klassen, s� vil den ikke gj�re noen ting.
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
		// Bilens �rsmodell
		public static final String COLUMN_YEAR = "year";
		// Bilens eier, som er en fremmedn�kkel til "user" databasen
		public static final String COLUMN_OWNER_FK_USER = "owner";
	}
}
