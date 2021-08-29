package it.lorenzopratesi.app.hotelsbooking.controller;

import it.lorenzopratesi.app.hotelsbooking.model.Company;
import it.lorenzopratesi.app.hotelsbooking.model.Employee;
import it.lorenzopratesi.app.hotelsbooking.repository.BookingPolicyRepository;
import it.lorenzopratesi.app.hotelsbooking.repository.BookingRepository;
import it.lorenzopratesi.app.hotelsbooking.repository.CompanyRepository;

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
