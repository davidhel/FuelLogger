package no.appfortress.fuellogger;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements OnItemClickListener{

	private DrawerLayout navDrawer;
	private ListView listDrawer;
	private FrameLayout contentFrame;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpActionBar();
	}

	private void setUpActionBar() {
		ActionBar actionbar = getSupportActionBar();
		ColorDrawable cd = new ColorDrawable(getResources().getColor(
				R.color.actionbar_background));
		actionbar.setBackgroundDrawable(cd);

		String[] navList = getResources().getStringArray(
				R.array.navigation_list);

		navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		listDrawer = (ListView) findViewById(R.id.lvNavDrawer);

		contentFrame = (FrameLayout) findViewById(R.id.content_frame);

		setContent();
		
		listDrawer.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, navList));

		
		navDrawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
		
		listDrawer.setOnItemClickListener(this);

		mTitle = mDrawerTitle = getTitle();

		mDrawerToggle = new ActionBarDrawerToggle(this, navDrawer,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View view) {
				super.onDrawerOpened(view);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}

		};

		navDrawer.setDrawerListener(mDrawerToggle);

		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
	}

	private void setContent() {
		
		FragmentManager fragmentManager = (FragmentManager)getSupportFragmentManager();
		fragmentManager.beginTransaction().add(R.id.content_frame, new RegisterFuelFragment()).commit();
	}

	// MENU
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(mDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
		if (id == R.id.action_settings) {
			Intent prefs = new Intent(this, Preferences.class);
			startActivity(prefs);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i;
		/*
		 * 0 : Cars
		 * 1 : Drive
		 * 2 : History
		 * 3 : Settings
		 */
		switch(position){
		case 0:
			i = new Intent(this, MyVehiclesActivity.class);
			break;
		case 1:
			i = new Intent(this, GPSActivity.class);
			break;
		case 2:
			return;
		case 3:
			i = new Intent(this, Preferences.class);
			break;
		default:
			return;
		}
		
		startActivity(i);
		
	}

}