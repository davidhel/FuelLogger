package no.appfortress.fuellogger;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.Toast;

public class VehiclesFragment extends Fragment implements OnTabChangeListener, OnPageChangeListener{

	
	
	FragmentTabHost tabHost;
	
	public final static String YOUR_VEHICLES = "Your Vehicles";
	public final static String NEW_VEHICLE = "New Vehicle";
	public final static int NUM_PAGES = 2;
	
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private TabWidget tabWidget;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.vehicles_fragment, container, false);
		tabHost =(FragmentTabHost) view.findViewById(R.id.tabhost);
		tabHost.setup(getActivity(), getChildFragmentManager(), R.id.flVehicleContent);
		
		
		tabHost.addTab(tabHost.newTabSpec(YOUR_VEHICLES).setIndicator(YOUR_VEHICLES), MyVehiclesFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec(NEW_VEHICLE).setIndicator(NEW_VEHICLE), RegisterVehicleFragment.class, null);
		tabHost.setOnTabChangedListener(this);
		
		
		viewPager = (ViewPager) view.findViewById(R.id.flVehicleContent);
		pagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);
		return view;
	}

	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{
		MyVehiclesFragment myVehicles;
		RegisterVehicleFragment registerVehicle ;
		
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
			myVehicles = new MyVehiclesFragment();
			registerVehicle = new RegisterVehicleFragment();
		}

		@Override
		public Fragment getItem(int position) {
			Log.d("position", position + "");	
			switch(position){
			case 0:
				return myVehicles;
			case 1:
				return registerVehicle;
			}
			return null;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
		
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d("Vehicles", "OnTabChanged");
		switch(tabId){
		case NEW_VEHICLE:
			if(viewPager.getCurrentItem() != 1){
				viewPager.setCurrentItem(1);
			}
			break;
		case YOUR_VEHICLES:
			if(viewPager.getCurrentItem() != 0){
				viewPager.setCurrentItem(0);
			}
			break;	
		}
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		int pos = viewPager.getCurrentItem();
		tabHost.setCurrentTab(pos);
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	
	
	
}
