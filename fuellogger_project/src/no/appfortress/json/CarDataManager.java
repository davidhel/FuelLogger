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

	private JSONObject jsonObject;
	private JSONObject test;
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
		String[] makes = null;
		/*try {
			
			JSONObject genresString = jsonObject.getJSONObject(1);
			//Log.d("JSONOBJECT", genresString.toString());
			//test = jsonObject.getJSONObject(tag_models);
			//Log.d("MAKES", jsonObject.toString());
			//String genresString = jsonObject.getJSONObject(tag_makes).getString(tag_models);
			/*JSONObject json = new JSONObject(genresString);
			Log.d("MAKES", genresString);
			Log.d("MAKES", json.getString("name"));
			//makes = genresString.split(",");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}*/
		return makes;
	}
	/*
	public String[] getModels(){
		String[] models;
		try {
			String genresString = jsonObject.getString(tag_models);
			models = genresString.split(",");
		} catch (JSONException e) {
			return null;
		}
		return models;
	}
	public boolean isSearchSuccess() {
		try {
			jsonObject.getString(tag_makes);
		} catch (JSONException e) {
			return false;
		}
		return true;
	}*/

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
				
				JSONObject json = new JSONObject(result.toString()).getJSONObject("makes");
				Log.d("String", result.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			caller.dataLoaded(result.toString());
		}

	}
}
