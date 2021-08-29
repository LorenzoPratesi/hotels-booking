package it.lorenzopratesi.app.hotelsbooking.repository;

import java.util.List;

import it.lorenzopratesi.app.hotelsbooking.model.Company;
import it.lorenzopratesi.app.hotelsbooking.model.Employee;
import it.lorenzopratesi.app.hotelsbooking.model.RoomType;

public interface BookingPolicyRepository {

	void deletePolicyOf(Employee employee);

	List<RoomType> findRoomsAllowedFor(String employeeId);

	List<RoomType> findRoomsAllowedFor(Company aCompany);

	void save(Company aCompany, List<RoomType> roomTypesAllowed);

	void save(Employee anEmployee, List<RoomType> roomTypesAllowed);

}
