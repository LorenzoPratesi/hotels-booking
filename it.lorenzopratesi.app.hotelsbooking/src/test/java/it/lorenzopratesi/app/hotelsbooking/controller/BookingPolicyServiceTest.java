package it.lorenzopratesi.app.hotelsbooking.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.lorenzopratesi.app.hotelsbooking.model.Company;
import it.lorenzopratesi.app.hotelsbooking.model.Employee;
import it.lorenzopratesi.app.hotelsbooking.model.RoomType;
import it.lorenzopratesi.app.hotelsbooking.repository.BookingPolicyRepository;
import it.lorenzopratesi.app.hotelsbooking.repository.CompanyRepository;

class BookingPolicyServiceTest {
	private static final Company A_COMPANY = new Company("1");
	private static final Employee AN_EMPLOYEE = new Employee("1");

	@Mock
	private BookingPolicyRepository bookingPolicyRepository;

	@Mock
	private CompanyRepository companyRepository;

	@InjectMocks
	private BookingPolicyService bookingPolicyService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testWhenNoEmployeeNorCompanyPolicyAreSetThenBookingIsAllowed() {
		when(bookingPolicyRepository.findRoomsAllowedFor(AN_EMPLOYEE.getId())).thenReturn(emptyList());
		when(bookingPolicyRepository.findRoomsAllowedFor(A_COMPANY)).thenReturn(emptyList());

		when(companyRepository.findCompanyFor(AN_EMPLOYEE.getId())).thenReturn(A_COMPANY);

		assertThat(bookingPolicyService.isBookingAllowed(AN_EMPLOYEE.getId(), RoomType.TWIN)).isTrue();
	}

	@Test
	public void testWhenNoEmployeePolicyIsSetAndCompanyPolicyIsSetForARoomTypeThenBookingIsAllowedForThatSameRoomType() {
		when(bookingPolicyRepository.findRoomsAllowedFor(AN_EMPLOYEE.getId())).thenReturn(emptyList());
		when(bookingPolicyRepository.findRoomsAllowedFor(A_COMPANY)).thenReturn(asList(RoomType.TWIN));

		when(companyRepository.findCompanyFor(AN_EMPLOYEE.getId())).thenReturn(A_COMPANY);

		assertThat(bookingPolicyService.isBookingAllowed(AN_EMPLOYEE.getId(), RoomType.TWIN)).isTrue();
	}
	
	@Test
	public void whenNoEmployeePolicyIsSetAndCompanyPolicyIsSetForARoomTypeThenBookingIsNotAllowedForADifferentRoomType() {
		when(bookingPolicyRepository.findRoomsAllowedFor(AN_EMPLOYEE.getId())).thenReturn(emptyList());
		when(companyRepository.findCompanyFor(AN_EMPLOYEE.getId())).thenReturn(A_COMPANY);

		when(bookingPolicyRepository.findRoomsAllowedFor(A_COMPANY)).thenReturn(asList(RoomType.SINGLE));

		assertThat(bookingPolicyService.isBookingAllowed(AN_EMPLOYEE.getId(), RoomType.DOUBLE)).isFalse();
	}
	
	@Test
	public void testWhenEmployeePolicyIsSetThenItTakesPrecedenceOverCompanyPolicy() {
		when(bookingPolicyRepository.findRoomsAllowedFor(AN_EMPLOYEE.getId())).thenReturn(asList(RoomType.TWIN));

		assertThat(bookingPolicyService.isBookingAllowed(AN_EMPLOYEE.getId(), RoomType.TWIN)).isTrue();
		verifyNoInteractions(companyRepository);
		verify(bookingPolicyRepository, times(0)).findRoomsAllowedFor(A_COMPANY);
	}
	
	@Test
	public void testWhenEmployeePolicyIsSetForARoomTypeThenBookingIsAllowedForThatSameRoomType() {
		when(bookingPolicyRepository.findRoomsAllowedFor(AN_EMPLOYEE.getId())).thenReturn(asList(RoomType.TWIN));

		assertThat(bookingPolicyService.isBookingAllowed(AN_EMPLOYEE.getId(), RoomType.TWIN)).isTrue();
	}
	
	@Test
	public void testWhenEmployeePolicyIsSetForARoomTypeThenBookingIsNotAllowedForADifferentRoomType() {
		when(bookingPolicyRepository.findRoomsAllowedFor(AN_EMPLOYEE.getId())).thenReturn(asList(RoomType.TWIN));

		assertThat(bookingPolicyService.isBookingAllowed(AN_EMPLOYEE.getId(), RoomType.SINGLE)).isFalse();
	}
	
	@Test
	public void testCompanyPolicyGetsSavedInRepository() {
		bookingPolicyService.setCompanyPolicy(A_COMPANY, asList(RoomType.TWIN));
		verify(bookingPolicyRepository).save(A_COMPANY, asList(RoomType.TWIN));
	}
	
	@Test
	public void testEmployeePolicyGetsSavedInRepository() {
		bookingPolicyService.setEmployeePolicy(AN_EMPLOYEE, asList(RoomType.DOUBLE));
		verify(bookingPolicyRepository).save(AN_EMPLOYEE, asList(RoomType.DOUBLE));
	}


}
