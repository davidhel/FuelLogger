package no.appfortress.fuellogger;


import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

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
	
	@Override
	protected void onResume() {
		super.onResume();
		// This checks if the device is compatible and up to date with Google Play Services
		int status =  GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		System.out.println(status);
	}

	public void btnRegister(View view){
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

}