package no.appfortress.fuellogger;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	
}