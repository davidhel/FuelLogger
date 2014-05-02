package no.appfortress.fuellogger;

import java.util.Calendar;

public class Refill {

	private long id;
	private Car car;
	private float fuelLitre, fuelPrice, latitude, longitude;
	private long odometer;
	private Calendar date;

	public Refill(long _id, Car _car, float _fuelLitre, float _fuelPrice,
			long _odometer, Calendar _date, float _latitude, float _longitude) {
		id = _id;
		car = _car;
		fuelLitre = _fuelLitre;
		fuelPrice = _fuelPrice;
		odometer = _odometer;
		date = _date;
		latitude = _latitude;
		longitude = _longitude;
	}

	public float getFuelPrice() {
		return (fuelPrice / fuelLitre);
	}

	@Override
	public String toString() {
		String dayOfMonth = (date.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + date.get(Calendar.DAY_OF_MONTH) : Integer.toString(date.get(Calendar.DAY_OF_MONTH));
		String month = (date.get(Calendar.MONTH) < 10 ) ? "0" + date.get(Calendar.MONTH) : Integer.toString(date.get(Calendar.MONTH));
		String year = Integer.toString(date.get(Calendar.YEAR));
		
		String rtnString = "";
		rtnString += dayOfMonth + "." + month + "." + year + " - ";
		rtnString += Float.toString(fuelLitre) + " liter, ";
		rtnString += "cost " + Float.toString(fuelPrice) + ".";
		return rtnString;
	}

	public Refill(long _id, Car _car, float _fuelLitre, float _fuelPrice,
			long _odometer) {
		this(_id, _car, _fuelLitre, _fuelPrice, _odometer, null,
				Float.MIN_VALUE, Float.MIN_VALUE);
	}

	public long getID() {
		return id;
	}

	public Car getCar() {
		return car;
	}

	public float getLitreFueled() {
		return fuelLitre;
	}

	public float getPrice() {
		return fuelPrice;
	}

	public long getOdometer() {
		return odometer;
	}

	public Calendar getDate() {
		return date;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

}
