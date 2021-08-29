package it.lorenzopratesi.app.hotelsbooking.repository;

import it.lorenzopratesi.app.hotelsbooking.model.Hotel;
import it.lorenzopratesi.app.hotelsbooking.model.RoomType;

public interface HotelRepository {

	public void addHotel(Hotel hotel);

	public Hotel findHotelById(String id);

	public void setRoom(Hotel hotel, int number, RoomType roomType);

}
