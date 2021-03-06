package it.lorenzopratesi.app.hotelsbooking.controller;

import java.time.LocalDate;
import static java.util.Arrays.*;
import static it.lorenzopratesi.app.hotelsbooking.model.Booking.Reason.*;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.lorenzopratesi.app.hotelsbooking.model.Booking;
import it.lorenzopratesi.app.hotelsbooking.model.Employee;
import it.lorenzopratesi.app.hotelsbooking.model.Hotel;
import it.lorenzopratesi.app.hotelsbooking.model.RoomType;
import it.lorenzopratesi.app.hotelsbooking.repository.BookingRepository;
import it.lorenzopratesi.app.hotelsbooking.validation.DatesValidator;

import static java.util.Collections.emptyList;

class BookingServiceTest {

	@Mock
	private HotelService hotelService;

	@Mock
	private BookingPolicyService bookingPolicyService;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private DatesValidator datesValidator;

	@InjectMocks
	private BookingService bookingService;

	private static final LocalDate EPOCH = LocalDate.of(1970, 1, 1);
	private LocalDate checkIn, checkOut;
	private Hotel hotel;
	private Employee employee;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);

		checkIn = EPOCH;
		checkOut = EPOCH.plus(1, DAYS);
		hotel = new Hotel("1", "HotelName");
		employee = new Employee("emp_1");

		when(datesValidator.validate(checkIn, checkOut)).thenReturn(true);
	}

	@Test
	void testBookShouldReturnsBookingFailureOfIncorrectDatesWhenValidatorSaysSo() {
		when(datesValidator.validate(checkIn, checkOut)).thenReturn(false);

		Booking bookingResult = createBookingFor(employee, hotel, null, checkIn, checkOut);

		assertThat(bookingResult.isBooked()).isFalse();
		assertThat(bookingResult.getReason()).isEqualTo(BAD_DATES);
	}

	@Test
	void testReturnsBookingFailureIfHotelDoesNotExist() {
		when(hotelService.findHotelBy(hotel.getId())).thenReturn(null);

		Booking bookingResult = createBookingFor(employee, hotel, null, checkIn, checkOut);

		assertThat(bookingResult.isBooked()).isFalse();
		assertThat(bookingResult.getReason()).isEqualTo(UNKNOWN_HOTEL);
	}

	@Test
	void testBookShouldReturnsBookingFailureOfUnavailableRoomTypeWhenHotelDoesNotHaveIt() {
		RoomType roomType = RoomType.SINGLE;

		when(hotelService.findHotelBy(hotel.getId())).thenReturn(hotel);

		Booking bookingResult = createBookingFor(employee, hotel, roomType, checkIn, checkOut);

		assertThat(bookingResult.isBooked()).isFalse();
		assertThat(bookingResult.getReason()).isEqualTo(UNAVAILABLE_ROOM_TYPE);
	}

	@Test
	void testBookShouldReturnsBookingFailureOfBookingDisallowedByPolicyIfBookingPolicyServiceSaysSo() {
		RoomType roomType = RoomType.SINGLE;
		hotel.setRooms(roomType, 1);

		when(hotelService.findHotelBy(hotel.getId())).thenReturn(hotel);
		when(bookingPolicyService.isBookingAllowed(employee.getId(), roomType)).thenReturn(false);
		Booking bookingResult = bookingService.book(employee.getId(), hotel.getId(), roomType, checkIn, checkOut);

		assertThat(bookingResult.isBooked()).isFalse();
		assertThat(bookingResult.getReason()).isEqualTo(Booking.Reason.BOOKING_DISALLOWED_BY_POLICY);
	}

	@Test
	void testBookShouldReturnsBookingFailureOfUnavailableRoomWhenNotAvailableOnGivenDates() {
		RoomType roomType = RoomType.SINGLE;
		hotel.setRooms(roomType, 1);

		when(hotelService.findHotelBy(hotel.getId())).thenReturn(hotel);
		when(bookingPolicyService.isBookingAllowed(employee.getId(), roomType)).thenReturn(true);
		when(bookingRepository.findExistingBookingsFor(hotel.getId(), roomType))
				.thenReturn(asList(new Booking(employee.getId(), hotel.getId(), roomType, checkIn, checkOut)));

		Booking bookingResult = createBookingFor(employee, hotel, roomType, checkIn, checkOut);

		assertThat(bookingResult.isBooked()).isFalse();
		assertThat(bookingResult.getReason()).isEqualTo(NO_MORE_ROOMS_AVAILABLE_ON_GIVEN_DATES);
	}

	@Test
	void testBookShouldReturnsBookingSuccessAndSavesBookingWhenNoBookingsHaveBeenMadeSoFar() {
		RoomType roomType = RoomType.SINGLE;
		hotel.setRooms(roomType, 1);

		when(hotelService.findHotelBy(hotel.getId())).thenReturn(hotel);
		when(bookingPolicyService.isBookingAllowed(null, roomType)).thenReturn(true);
		when(bookingRepository.findExistingBookingsFor(hotel.getId(), roomType)).thenReturn(emptyList());

		Booking bookingResult = bookingService.book(null, hotel.getId(), roomType, checkIn, checkOut);

		assertThat(bookingResult.isBooked()).isTrue();
		assertThat(bookingResult.getReason()).isEqualTo(SUCCESS);
		verify(bookingRepository).save(bookingResult);

	}

	@Test
	void testBookShouldReturnsBookingSuccessAndSavesBookingWhenDatesAreNotOverlapping() {
		RoomType roomType = RoomType.SINGLE;
		hotel.setRooms(roomType, 1);

		when(hotelService.findHotelBy(hotel.getId())).thenReturn(hotel);
		when(bookingPolicyService.isBookingAllowed(employee.getId(), roomType)).thenReturn(true);
		when(bookingRepository.findExistingBookingsFor(hotel.getId(), roomType))
				.thenReturn(asList(new Booking(employee.getId(), hotel.getId(), roomType, checkOut.plus(1, DAYS), checkOut.plus(2, DAYS))));

		Booking bookingResult = createBookingFor(employee, hotel, roomType, checkIn, checkOut);

		assertThat(bookingResult.isBooked()).isTrue();
		assertThat(bookingResult.getReason()).isEqualTo(SUCCESS);
		verify(bookingRepository).save(bookingResult);
	}

	@Test
	void testBookShouldReturnsBookingSuccessAndSavesBookingWhenEnoughRoomsEvenIfDatesOverlap() {
		RoomType roomType = RoomType.SINGLE;
		hotel.setRooms(roomType, 2);

		when(hotelService.findHotelBy(hotel.getId())).thenReturn(hotel);
		when(bookingPolicyService.isBookingAllowed(employee.getId(), roomType)).thenReturn(true);
		when(bookingRepository.findExistingBookingsFor(hotel.getId(), roomType))
				.thenReturn(asList(new Booking(employee.getId(), hotel.getId(), roomType, checkIn, checkOut)));

		Booking bookingResult = createBookingFor(employee, hotel, roomType, checkIn, checkOut);

		assertThat(bookingResult.isBooked()).isTrue();
		assertThat(bookingResult.getReason()).isEqualTo(SUCCESS);
		verify(bookingRepository).save(bookingResult);
	}

	private Booking createBookingFor(Employee employee, Hotel hotel, RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
		return bookingService.book(employee.getId(), hotel.getId(), roomType, checkIn, checkOut);
	}

}
