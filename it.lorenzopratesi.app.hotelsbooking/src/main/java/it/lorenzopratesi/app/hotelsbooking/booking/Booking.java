package it.lorenzopratesi.app.hotelsbooking.booking;

import java.time.LocalDate;

import it.lorenzopratesi.app.hotelsbooking.hotel.RoomType;

public class Booking {

	public enum Reason {
		UNKNOWN_HOTEL, UNAVAILABLE_ROOM_TYPE, SUCCESS, NO_MORE_ROOMS_AVAILABLE_ON_GIVEN_DATES,
		BOOKING_DISALLOWED_BY_POLICY, BAD_DATES
	}

	private String employeeId;
	private String hotelId;
	private RoomType roomType;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private boolean isBooked;
	private Reason reason;

	public Booking(String employeeId, String hotelId, RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
		this.employeeId = employeeId;
		this.hotelId = hotelId;
		this.roomType = roomType;
		this.checkIn = checkIn;
		this.checkOut = checkOut;

		isBooked = false;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getHotelId() {
		return hotelId;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public boolean overlaps(Booking booking) {
		
		if (booking.getCheckOut().isBefore(this.checkIn) || booking.getCheckOut().isEqual(this.checkIn)) {
			return false;
		}
		if (booking.getCheckIn().isEqual(this.checkOut) || booking.getCheckIn().isAfter(this.checkOut)) {
			return false;
		}
		return true;
	}

}
