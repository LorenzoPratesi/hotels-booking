package it.lorenzopratesi.app.hotelsbooking.company;

import it.lorenzopratesi.app.hotelsbooking.booking.BookingRepository;
import it.lorenzopratesi.app.hotelsbooking.employee.Employee;
import it.lorenzopratesi.app.hotelsbooking.policy.BookingPolicyRepository;

public class CompanyService {

	private CompanyRepository companyRepository;
	private BookingPolicyRepository bookingPolicyRepository;
	private BookingRepository bookingRepository;

	public void addEmployee(Company company, Employee employee) {
		Company existingCompany = companyRepository.findCompanyFor(employee.getId()); 
		if(existingCompany != null) {
			throw new RuntimeException("Employee with id " + employee.getId() + " already exists.");
		}
		companyRepository.addEmployee(company, employee);
	}

	public void deleteEmployee(Employee employee) {
		companyRepository.deleteEmployee(employee);
		bookingPolicyRepository.deletePolicyOf(employee);
		bookingRepository.deleteBookingsOf(employee);
	}

}
