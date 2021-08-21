package it.lorenzopratesi.app.hotelsbooking.policy;

import java.util.Collection;
import java.util.List;

import it.lorenzopratesi.app.hotelsbooking.company.Company;
import it.lorenzopratesi.app.hotelsbooking.company.CompanyRepository;
import it.lorenzopratesi.app.hotelsbooking.employee.Employee;
import it.lorenzopratesi.app.hotelsbooking.hotel.RoomType;

public class BookingPolicyService {

	private BookingPolicyRepository bookingPolicyRepository;
	private CompanyRepository companyRepository;

	public boolean isBookingAllowed(String employeeId, RoomType roomType) {
		Collection<RoomType> roomsAllowedForEmployee = bookingPolicyRepository.findRoomsAllowedFor(employeeId);
		
		if (policySet(roomsAllowedForEmployee)) {
			return roomsAllowedForEmployee.contains(roomType);
		}
		
		Company findCompanyFor = companyRepository.findCompanyFor(employeeId);
		
		List<RoomType> roomsAllowedByCompany = bookingPolicyRepository.findRoomsAllowedFor(findCompanyFor);
		
		return policySet(roomsAllowedByCompany) ? roomsAllowedByCompany.contains(roomType) : true;
	}
	
	private boolean policySet(Collection<RoomType> roomsAllowed) {
		return !roomsAllowed.isEmpty();
	}

	public void setCompanyPolicy(Company aCompany, List<RoomType> roomTypesAllowed) {
		bookingPolicyRepository.save(aCompany, roomTypesAllowed);
	}

	public void setEmployeePolicy(Employee anEmployee, List<RoomType> roomTypesAllowed) {
		bookingPolicyRepository.save(anEmployee, roomTypesAllowed);
	}

}
