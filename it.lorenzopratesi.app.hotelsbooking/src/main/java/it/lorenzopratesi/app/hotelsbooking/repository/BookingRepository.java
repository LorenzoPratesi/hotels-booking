package it.lorenzopratesi.app.hotelsbooking.repository;

import java.util.List;

import it.lorenzopratesi.app.hotelsbooking.model.Booking;
import it.lorenzopratesi.app.hotelsbooking.model.Employee;
import it.lorenzopratesi.app.hotelsbooking.model.RoomType;

public interface BookingRepository {

	void deleteBookingsOf(Employee employee);
	
    public List<Booking> findExistingBookingsFor(String hotelId, RoomType roomType);

	void save(Booking bookingResult);

}
