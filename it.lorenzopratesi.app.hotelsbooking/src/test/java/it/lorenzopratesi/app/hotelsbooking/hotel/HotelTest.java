package it.lorenzopratesi.app.hotelsbooking.hotel;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HotelTest {

	@Test
	void testNumberOfRoomsOfCanUpdateNumberOfRoomsForARoomType() {
		Hotel hotel = new Hotel("id", "name");
		assertThat(hotel.numberOfRoomsOf(RoomType.SINGLE)).isZero();
		assertThat(hotel.numberOfRoomsOf(RoomType.DOUBLE)).isZero();

		hotel.setRooms(RoomType.SINGLE, 5);
		hotel.setRooms(RoomType.DOUBLE, 42);
		assertThat(hotel.numberOfRoomsOf(RoomType.SINGLE)).isEqualTo(5);
		assertThat(hotel.numberOfRoomsOf(RoomType.DOUBLE)).isEqualTo(42);
	}

	@Test
	void testSetRoomsThrowsExceptionWhenNumberOfRoomsIsZero() {
		Hotel hotel = new Hotel("id", "name");
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> hotel.setRooms(RoomType.SINGLE, 0));
		assertThat(e.getMessage()).isEqualTo("Number of rooms cannot be less or equal to zero.");
	}
	
	@Test
	void testSetRoomsThrowsExceptionWhenNumberOfRoomsIsNegative() {
		Hotel hotel = new Hotel("id", "name");
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> hotel.setRooms(RoomType.SINGLE, -1));
		assertThat(e.getMessage()).isEqualTo("Number of rooms cannot be less or equal to zero.");
	}
}
