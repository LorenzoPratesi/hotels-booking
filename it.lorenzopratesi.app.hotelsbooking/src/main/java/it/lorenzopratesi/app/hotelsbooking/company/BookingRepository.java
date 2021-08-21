package it.lorenzopratesi.app.hotelsbooking.company;

import it.lorenzopratesi.app.hotelsbooking.employee.Employee;

public interface BookingRepository {

	void deleteBookingsOf(Employee employee);

}
