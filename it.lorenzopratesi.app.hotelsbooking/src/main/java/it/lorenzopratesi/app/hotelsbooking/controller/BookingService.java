package it.lorenzopratesi.app.hotelsbooking.controller;

import java.time.LocalDate;
import java.util.List;

import it.lorenzopratesi.app.hotelsbooking.model.Booking;
import it.lorenzopratesi.app.hotelsbooking.model.Hotel;
import it.lorenzopratesi.app.hotelsbooking.model.RoomType;
import it.lorenzopratesi.app.hotelsbooking.repository.BookingRepository;
import it.lorenzopratesi.app.hotelsbooking.validation.DatesValidator;

import static it.lorenzopratesi.app.hotelsbooking.model.Booking.Reason.*;
import static java.util.stream.Collectors.*;

public class BookingService {

	private DatesValidator datesValidator;
	private HotelService hotelService;
	private BookingPolicyService bookingPolicyService;
	private BookingRepository bookingRepository;

	public Booking book(String employeeId, String hotelId, RoomType roomType, LocalDate checkIn, LocalDate checkOut) {

		Booking booking = new Booking(employeeId, hotelId, roomType, checkIn, checkOut);

		if (!datesValidator.validate(checkIn, checkOut)) {
			booking.setReason(BAD_DATES);
			return booking;
		}

		Hotel hotel = hotelService.findHotelBy(hotelId);
		if (hotel == null) {
			booking.setReason(UNKNOWN_HOTEL);
			return booking;
		}

		int roomsOfType = hotel.numberOfRoomsOf(roomType);
		if (roomsOfType == 0) {
			booking.setReason(UNAVAILABLE_ROOM_TYPE);
			return booking;
		}

		if (!bookingPolicyService.isBookingAllowed(employeeId, roomType)) {
			booking.setReason(BOOKING_DISALLOWED_BY_POLICY);
			return booking;
		}

		List<Booking> overlappingBookings = bookingRepository.findExistingBookingsFor(hotelId, roomType)
				.stream()
				.filter(b -> b.overlaps(booking))
				.collect(toList());
		
		if (overlappingBookings.size() == roomsOfType) {
			booking.setReason(NO_MORE_ROOMS_AVAILABLE_ON_GIVEN_DATES);
			return booking;
		}
		
		booking.setReason(SUCCESS);
		booking.setBooked(true);
        bookingRepository.save(booking);
		return booking;
	}

}
