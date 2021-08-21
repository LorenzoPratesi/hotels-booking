package it.lorenzopratesi.app.hotelsbooking.company;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import it.lorenzopratesi.app.hotelsbooking.employee.Employee;
import it.lorenzopratesi.app.hotelsbooking.policy.BookingPolicyRepository;

class CompanyServiceTest {

	@Mock
	private CompanyRepository companyRepository;

	@Mock
	private BookingPolicyRepository bookingPolicyRepository;

	@Mock
	private BookingRepository bookingRepository;

	@InjectMocks
	private CompanyService companyService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testEmployeesAreStoredInTheEmployeeRepository() {
		Company company = new Company("1");
		Employee employee = new Employee("1");
		companyService.addEmployee(company, employee);
		verify(companyRepository).addEmployee(company, employee);
	}

	@Test
	public void testEmployeesCannotBeDuplicated() {
		Company company = new Company("1");
		Employee employee = new Employee("1");
		when(companyRepository.findCompanyFor(employee.getId())).thenReturn(company);

		assertThatThrownBy(() -> companyService.addEmployee(company, employee)).isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void testWhenEmployeeGetsDeletedThenEmployeeGetsDeletedFromRepoAndPoliciesAndBookingsForThatEmployeeGetDeletedToo() {
		Employee employee = new Employee("1");
		companyService.deleteEmployee(employee);

		verify(companyRepository).deleteEmployee(employee);
		verify(bookingPolicyRepository).deletePolicyOf(employee);
		verify(bookingRepository).deleteBookingsOf(employee);
	}

}
