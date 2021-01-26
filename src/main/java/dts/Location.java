package dts;

public class Location {
	
	private Double lat;
	private Double lng;
	
	public Location() {
		this.lat = 0.40;
		this.lng = 0.40;
	}
	public Location(Double lat, Double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
	

}
