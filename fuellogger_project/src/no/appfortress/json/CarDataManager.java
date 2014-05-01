package no.appfortress.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import no.appfortress.fuellogger.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

public class CarDataManager {

	public interface OnVehicleRequestListener {
		public void dataLoaded(String result);
	}

	private final static String APIKEY = "xyvjjvsayxn6wc4456524z83";
	private String make;
	private String model;
	
	public static String tag_makes = "makes";
	public static String tag_models = "name";

	private OnVehicleRequestListener caller;

	private JSONArray jsonArray;
	private static final String JSONMAKESURL = "http://api.edmunds.com/api/vehicle/v2/makes?fmt=json&api_key="
			+ APIKEY + "&state=new&view=full";
	private  String jSONModelsURL = "https://api.edmunds.com/api/vehicle/v2/"
			+ make + "/models?fmt=json&api_key=" + APIKEY;

	public CarDataManager(OnVehicleRequestListener _caller) {
		caller = _caller;
		
	}
	public void downloadMakes(){
		new SearchForVehicleTask().execute(JSONMAKESURL);
	}
	
	
	public void downloadModelsByMake(String _make){
		make = _make;
		new SearchForVehicleTask().execute(jSONModelsURL);
	}

	
	public String[] getMakes(){
		if(jsonArray == null){
			return new String[0];
		}
		String[] makes = new String[jsonArray.length()];
		try {
			
			for(int i = 0; i < jsonArray.length(); i++){
				
				makes[i] = jsonArray.getJSONObject(i).getString("name");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return makes;
	}
	
	public String[] getModels(String make){
		
		String[] models = null;
		JSONArray jsonArr = null;
		try {
			
			for(int i = 0; i < jsonArray.length(); i++){
				
				if(jsonArray.getJSONObject(i).getString("name").equals(make)){
					jsonArr = jsonArray.getJSONObject(i).getJSONArray("models");
				}
			}
			if(jsonArr == null){
				return null;
			}
			models = new String[jsonArr.length()];
			for(int i = 0; i < jsonArr.length(); i++){
				models[i] = jsonArr.getJSONObject(i).getString("name");
				Log.d("String", models[i]);
			}
		} catch (JSONException e) {
			return null;
		}
		return models;
	}
	
	

	protected StringBuilder getJSONString(String search) throws IOException {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		try {
			URLEncoder.encode(search.trim(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return new StringBuilder("Invalid URL");
		}
		HttpGet httpGet;

		try {
			httpGet = new HttpGet(search.replaceAll("\\s+", "%20"));
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return new StringBuilder("Invalid parameter");
		}
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(MainActivity.class.toString(), "Failed to downlad file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		return builder;
	}

	private class SearchForVehicleTask extends AsyncTask<String, Void, StringBuilder> {

		@Override
		protected StringBuilder doInBackground(String... params) {

			try {
				StringBuilder jsonString = getJSONString(params[0]);
				return jsonString;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(StringBuilder result) {
			try {
				
				jsonArray =  new JSONObject(result.toString()).getJSONArray("makes");
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			caller.dataLoaded(result.toString());
		}

	}
}
