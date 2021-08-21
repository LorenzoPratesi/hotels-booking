package it.lorenzopratesi.app.hotelsbooking.company;

import it.lorenzopratesi.app.hotelsbooking.employee.Employee;

public interface CompanyRepository {

	public void addEmployee(Company company, Employee employee);

	public Company findCompanyFor(String employeeId);

	public void deleteEmployee(Employee employee);

}
