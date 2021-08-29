package it.lorenzopratesi.app.hotelsbooking.controller;

import java.util.Collection;
import java.util.List;

import it.lorenzopratesi.app.hotelsbooking.model.Company;
import it.lorenzopratesi.app.hotelsbooking.model.Employee;
import it.lorenzopratesi.app.hotelsbooking.model.RoomType;
import it.lorenzopratesi.app.hotelsbooking.repository.BookingPolicyRepository;
import it.lorenzopratesi.app.hotelsbooking.repository.CompanyRepository;

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
