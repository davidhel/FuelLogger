package no.appfortress.fuellogger;

public class Car {
	
	private long id;
	private String brand;
	private String model;
	private int year;
	private int odometer;
	private float fuelTank;
	private float fuel;

	public Car(long _id,String _brand, String _model, int _year, int _odometer,
			float _fuelTank) {
		id = _id;
		brand = _brand;
		model = _model;
		year = _year;
		odometer = _odometer;
		fuelTank = _fuelTank;
	}
	
	
	
	
	@Override
	public String toString() {
		String rtnString = "";
		rtnString += Long.toString(id) + ". ";
		rtnString += brand + " ";
		if(model != null){
			rtnString += model + " ";
		}
		if(year != 0){
			rtnString += Integer.toString(year) + " ";
		}
		rtnString += Long.toString(odometer) + " ";
		rtnString += Float.toString(fuelTank) + " ";
		rtnString += Float.toString(fuel);
		return rtnString;
	}



	public void setFuel(float _fuel){
		fuel = _fuel;
	}
		
	public void addFuel(float _fuel){
		fuel += _fuel;
	}
	
	public void addOdometer(int newOdometer){
		odometer = newOdometer;
	}
	
	public long getID(){
		return id;
	}
	
	public String getBrand(){
		return brand;
	}
	
	public String getModel(){
		return model;
	}
	
	public int getYear(){
		return year;
	}
	
	public int getOdometer(){
		return odometer;
	}
	
	public float getFuel(){
		return fuel;
	}
	
	public float getFuelTank(){
		return fuelTank;
	}

}
