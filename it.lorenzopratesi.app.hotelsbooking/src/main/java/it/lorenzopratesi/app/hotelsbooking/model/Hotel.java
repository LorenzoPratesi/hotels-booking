package it.lorenzopratesi.app.hotelsbooking.model;

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

	public Map<RoomType, Integer> getRooms() {
		return rooms;
	}
	
	public void setRooms(RoomType roomType, int quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException("Number of rooms cannot be less or equal to zero.");
		}
		rooms.put(roomType, quantity);
	}

	public int numberOfRoomsOf(RoomType roomType) {
		return rooms.getOrDefault(roomType, 0);
	}
	
	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rooms == null) ? 0 : rooms.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rooms == null) {
			if (other.rooms != null)
				return false;
		} else if (!rooms.equals(other.rooms))
			return false;
		return true;
	}

	

}
