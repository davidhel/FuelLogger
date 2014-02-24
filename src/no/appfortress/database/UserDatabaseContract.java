package no.appfortress.database;

import android.provider.BaseColumns;
/**
 * 
 * @author Morgan
 * En kontrakt klasse for tabellen "user".
 * 
 * Den vil holde alle verdiene som skal være i tabellen "user", både navn
 * og kolonner til tabellen.
 * 
 * Denne tabellen vil inneholde alle brukerne i appen. Det vil være
 * mulighet å ha flere brukere i denne appen, men det vil også være mulig
 * å skjekke om det er første gang man bruker appen eller ikke, ettersom man
 * må ha en bruker for å kunne bruke appen.
 */
public class UserDatabaseContract {
	
	/**
 	 * Konstruktør som vil sørge for at hvis noen finner på å instansiere
 	 * klassen, så vil den ikke gjøre noen ting.
 	 */
	public UserDatabaseContract(){}
	/**
	 * 
	 * @author Morgan
	 *
	 *	En klasse som holder verdiene til tabellen
	 */
	public static abstract class UserFeedEntry implements BaseColumns{
		// Navn på tabellen
		public static final String TABLE_NAME = "user";
		// Id til brukeren, AUTO INCREMENT
		public static final String COLUMN_USER_ID = "user_id";
		// Navnet til Brukeren
		public static final String COLUMN_NAME = "name";
		
		// TODO Trengs det flere kolonner for brukeren?
	}
	
}
