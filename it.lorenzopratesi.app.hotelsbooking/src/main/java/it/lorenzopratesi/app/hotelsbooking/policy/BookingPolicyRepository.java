package it.lorenzopratesi.app.hotelsbooking.policy;

import java.util.List;

import it.lorenzopratesi.app.hotelsbooking.company.Company;
import it.lorenzopratesi.app.hotelsbooking.employee.Employee;
import it.lorenzopratesi.app.hotelsbooking.hotel.RoomType;

public interface BookingPolicyRepository {

	void deletePolicyOf(Employee employee);

	List<RoomType> findRoomsAllowedFor(String employeeId);

	List<RoomType> findRoomsAllowedFor(Company aCompany);

	void save(Company aCompany, List<RoomType> roomTypesAllowed);

	void save(Employee anEmployee, List<RoomType> roomTypesAllowed);

}
