package no.appfortress.fuellogger;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VehiclesFragment extends Fragment{

	FragmentTabHost tabHost;
	
	public final static String YOUR_VEHICLES = "Your Vehicles";
	public final static String NEW_VEHICLE = "New Vehicle";
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.vehicles_fragment, container, false);
		tabHost =(FragmentTabHost) view.findViewById(R.id.tabhost);
		tabHost.setup(getActivity(), getChildFragmentManager(), R.id.flVehicleContent);
		
		tabHost.addTab(tabHost.newTabSpec(YOUR_VEHICLES).setIndicator(YOUR_VEHICLES), MyVehiclesFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec(NEW_VEHICLE).setIndicator(NEW_VEHICLE), RegisterVehicleFragment.class, null);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Activity a = getActivity();
		//tabHost = (FragmentTabHost) a.findViewById(R.id.tabhost);
		//tabHost.setup(a, getFragmentManager(), R.id.flVehicleContent);
	}
	
	
}
