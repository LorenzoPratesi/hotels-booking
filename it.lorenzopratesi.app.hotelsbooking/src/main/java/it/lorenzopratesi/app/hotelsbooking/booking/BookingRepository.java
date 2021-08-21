package it.lorenzopratesi.app.hotelsbooking.booking;

import java.util.List;

import it.lorenzopratesi.app.hotelsbooking.employee.Employee;
import it.lorenzopratesi.app.hotelsbooking.hotel.RoomType;

public interface BookingRepository {

	void deleteBookingsOf(Employee employee);
	
    public List<Booking> findExistingBookingsFor(String hotelId, RoomType roomType);

	void save(Booking bookingResult);

}
