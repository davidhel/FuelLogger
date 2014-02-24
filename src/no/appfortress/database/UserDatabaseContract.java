package no.appfortress.database;

import no.appfortress.fuellogger.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
	
	private static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + UserFeedEntry.TABLE_NAME + " ("
			+ UserFeedEntry._ID + " INTEGER PRIMARY KEY, "
			+ UserFeedEntry.COLUMN_NAME + " VARCHAR(255) NOT NULL)";
	
	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " +  UserFeedEntry.TABLE_NAME;
	
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
		// Navnet til Brukeren
		public static final String COLUMN_NAME = "name";
		
		// TODO Trengs det flere kolonner for brukeren?
	}
	
	public class UserDbHelper extends SQLiteOpenHelper{
		
		public UserDbHelper(Context c){
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
