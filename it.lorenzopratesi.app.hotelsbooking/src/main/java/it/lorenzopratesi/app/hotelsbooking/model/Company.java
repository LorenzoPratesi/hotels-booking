package it.lorenzopratesi.app.hotelsbooking.model;

public class Company {

	private String id;

	public Company(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + "]";
	}

}
