package it.lorenzopratesi.app.hotelsbooking.model;

public class Employee {

	private String id;

	public Employee(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + "]";
	}
}
