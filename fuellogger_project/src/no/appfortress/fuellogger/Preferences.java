package no.appfortress.fuellogger;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class Preferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT <= 10) {
			addPreferencesFromResource(R.xml.preference_screen);
		} else {
			startFragment();
		}

	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void startFragment(){
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFragment()).commit();
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class PreferencesFragment extends PreferenceFragment {
		@Override
		public void onCreate(final Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference_screen);
		}
	}
}
