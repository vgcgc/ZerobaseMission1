package db;

public class History {
	
	private String id;
	private double lat;
	private double lnt;
	private String dateStamp;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLat() {
		return Double.toString(lat);
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getLnt() {
		return Double.toString(lnt);
	}
	public void setLnt(double lnt) {
		this.lnt = lnt;
	}
	public String getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(String dateStamp) {
		this.dateStamp = dateStamp;
	}
	
	

}
