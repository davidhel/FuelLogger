package no.appfortress.database;

import android.provider.BaseColumns;
/**
 * 
 * @author Morgan
 * En kontrakt klasse for tabellen "user".
 * 
 * Den vil holde alle verdiene som skal v�re i tabellen "user", b�de navn
 * og kolonner til tabellen.
 * 
 * Denne tabellen vil inneholde alle brukerne i appen. Det vil v�re
 * mulighet � ha flere brukere i denne appen, men det vil ogs� v�re mulig
 * � skjekke om det er f�rste gang man bruker appen eller ikke, ettersom man
 * m� ha en bruker for � kunne bruke appen.
 */
public class UserDatabaseContract {
	
	/**
 	 * Konstrukt�r som vil s�rge for at hvis noen finner p� � instansiere
 	 * klassen, s� vil den ikke gj�re noen ting.
 	 */
	public UserDatabaseContract(){}
	/**
	 * 
	 * @author Morgan
	 *
	 *	En klasse som holder verdiene til tabellen
	 */
	public static abstract class UserFeedEntry implements BaseColumns{
		// Navn p� tabellen
		public static final String TABLE_NAME = "user";
		// Id til brukeren, AUTO INCREMENT
		public static final String COLUMN_USER_ID = "user_id";
		// Navnet til Brukeren
		public static final String COLUMN_NAME = "name";
		
		// TODO Trengs det flere kolonner for brukeren?
	}
	
}
