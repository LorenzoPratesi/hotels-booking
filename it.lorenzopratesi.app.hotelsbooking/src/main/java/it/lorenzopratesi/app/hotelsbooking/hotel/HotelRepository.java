package it.lorenzopratesi.app.hotelsbooking.hotel;

public interface HotelRepository {

	public void addHotel(Hotel hotel);

	public Hotel findById(String id);

	public void setRoom(Hotel hotel, int number, RoomType roomType);

}
