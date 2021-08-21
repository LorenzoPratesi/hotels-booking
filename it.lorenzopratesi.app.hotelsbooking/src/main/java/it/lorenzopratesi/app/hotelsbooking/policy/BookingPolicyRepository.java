package it.lorenzopratesi.app.hotelsbooking.policy;

import it.lorenzopratesi.app.hotelsbooking.employee.Employee;

public interface BookingPolicyRepository {

	void deletePolicyOf(Employee employee);

}
