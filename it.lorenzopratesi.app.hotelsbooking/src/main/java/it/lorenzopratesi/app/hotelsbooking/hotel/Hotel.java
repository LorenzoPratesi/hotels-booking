package it.lorenzopratesi.app.hotelsbooking.hotel;

import java.util.HashMap;
import java.util.Map;

public class Hotel {

	private String id;
	private String name;
	private final Map<RoomType, Integer> rooms = new HashMap<>();


	public Hotel(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRooms(RoomType roomType, int number) {
		rooms.put(roomType, number);
	}

	public Map<RoomType, Integer> getRooms() {
		return rooms;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + "]";
	}

}
