package it.lorenzopratesi.app.hotelsbooking.repository;

import it.lorenzopratesi.app.hotelsbooking.model.Company;
import it.lorenzopratesi.app.hotelsbooking.model.Employee;

public interface CompanyRepository {

	public void addEmployee(Company company, Employee employee);

	public Company findCompanyFor(String employeeId);

	public void deleteEmployee(Employee employee);

}
