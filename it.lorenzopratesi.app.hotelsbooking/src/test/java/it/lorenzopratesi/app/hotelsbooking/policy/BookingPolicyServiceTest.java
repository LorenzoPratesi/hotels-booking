package it.lorenzopratesi.app.hotelsbooking.policy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.lorenzopratesi.app.hotelsbooking.company.Company;
import it.lorenzopratesi.app.hotelsbooking.company.CompanyRepository;
import it.lorenzopratesi.app.hotelsbooking.employee.Employee;

class BookingPolicyServiceTest {
	private static final Company A_COMPANY = new Company("1");
	private static final Employee AN_EMPLOYEE = new Employee("1");

	@Mock
	private BookingPolicyRepository bookingPolicyRepository;
	
	@Mock
	private CompanyRepository companyRepository;
	
	@InjectMocks
	private BookingPolicyService service;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testWhenNoEmployeeNorCompanyPolicyAreSetThenBookingIsAllowed() {

	}

}
