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
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class CarDataManager {

	public interface OnVehicleRequestListener {
		public void dataLoaded();
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
			+ APIKEY;
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
		String[] makes;
		try {
			String genresString = jsonObject.getJSONObject(tag_makes).getString(tag_models);
			test = new JSONObject("Your string here").getJSONObject(tag_makes);
			//test = jsonObject.getJSONObject(tag_models);
			Log.d("MAKES", "hei verden");
			Log.d("MAKES", test.toString());
			//Log.d("MAKES", jsonObject.toString());
			//String genresString = jsonObject.getJSONObject(tag_makes).getString(tag_models);
			/*JSONObject json = new JSONObject(genresString);
			Log.d("MAKES", genresString);
			Log.d("MAKES", json.getString("name"));*/
			makes = genresString.split(",");
		} catch (JSONException e) {
			return null;
		}
		return makes;
	}
	
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
	}

	protected String getJSONString(String search) throws IOException {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		try {
			URLEncoder.encode(search.trim(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return "Invalid URL";
		}
		HttpGet httpGet;

		try {
			httpGet = new HttpGet(search.replaceAll("\\s+", "%20"));
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return "Invalid parameter";
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
		return builder.toString();
	}

	private class SearchForVehicleTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			try {
				String jsonString = getJSONString(params[0]);
				jsonObject = new JSONObject(jsonString);
				return jsonString;
			} catch (IOException ex) {
				return "Couldn't retrive web page.";
			} catch (JSONException ex) {
				ex.printStackTrace();
				return "Couldn't parse JSONObject";
			}

		}

		@Override
		protected void onPostExecute(String result) {
			caller.dataLoaded();
		}

	}
}
