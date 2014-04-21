package no.appfortress.fuellogger;

import no.appfortress.gps.MyGoogleMaps;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpActionBar();
	}

	private void setUpActionBar() {
		ActionBar actionbar = getSupportActionBar();
		ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.actionbar_background));
		actionbar.setBackgroundDrawable(cd);
	}

	public void btnRegister(View view) {
		Intent intent = new Intent(this, RegisterVehicleActivity.class);
		startActivity(intent);
	}

	public void btnAddFueling(View view) {
		Intent intent = new Intent(this, RegisterFuelingActivity.class);
		startActivity(intent);
	}

	public void btnDatabaseClick(View view){
		Intent i = new Intent(this, DatabaseActivity.class);
		startActivity(i);
	}
	
	public void btnGPS(View view){
		Intent intent = new Intent(this, GPSActivity.class);
		startActivity(intent);
	}
	
	public void btnMap(View view){
		Intent intent = new Intent(this, MyGoogleMaps.class);
		startActivity(intent);
	}
	public void btnCalculate(View view){
		Intent intent = new Intent(this, TripCalculatorActivity.class);
		startActivity(intent);
	}
	//MENU
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Log.d("menu", "menu");
			Intent prefs = new Intent(this, Preferences.class);
			startActivity(prefs);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}