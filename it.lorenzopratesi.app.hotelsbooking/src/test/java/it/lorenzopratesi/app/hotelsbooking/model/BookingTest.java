package it.lorenzopratesi.app.hotelsbooking.model;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class BookingTest {
	private static final LocalDate EPOCH = LocalDate.of(1970, 1, 1);

	@Test
	void testOverlappingBookingsOnExactMatch() {
		Booking booking1 = new Booking("1", "1", RoomType.SINGLE, EPOCH, EPOCH.plus(2, DAYS));
		Booking booking2 = new Booking("1", "1", RoomType.SINGLE, EPOCH, EPOCH.plus(2, DAYS));

		assertThat(booking1.overlaps(booking2)).isTrue();
	}
	
	@Test
	void testOverlappingBookingsOnCheckoutSideWhenCheckoutIsBefore() {
		Booking booking1 = new Booking("1", "1", RoomType.SINGLE, EPOCH, EPOCH.plus(2, DAYS));
		Booking booking2 = new Booking("1", "1", RoomType.SINGLE, booking1.getCheckIn().minus(1, DAYS), booking1.getCheckIn().plus(1, DAYS));

		assertThat(booking1.overlaps(booking2)).isTrue();
	}
	
	@Test
	void testOverlappingBookingsWhenCheckoutIsTheSame() {
		Booking booking1 = new Booking("1", "1", RoomType.SINGLE, EPOCH, EPOCH.plus(2, DAYS));
		Booking booking2 = new Booking("1", "1", RoomType.SINGLE, booking1.getCheckIn().minus(1, DAYS), booking1.getCheckOut());

		assertThat(booking1.overlaps(booking2)).isTrue();
	}
	
	@Test
	void testOverlappingBookingsOnCheckoutSideWhenCheckoutIsAfter() {
		Booking booking1 = new Booking("1", "1", RoomType.SINGLE, EPOCH, EPOCH.plus(1, DAYS));
		Booking booking2 = new Booking("1", "1", RoomType.SINGLE, booking1.getCheckIn().minus(1, DAYS), booking1.getCheckOut().plus(1, DAYS));

		assertThat(booking1.overlaps(booking2)).isTrue();
	}
	
	@Test
	void testOverlappingBookingsOnCheckinSideWhenCheckinIsTheSame() {
		Booking booking1 = new Booking("1", "1", RoomType.SINGLE, EPOCH, EPOCH.plus(2, DAYS));
		Booking booking2 = new Booking("1", "1", RoomType.SINGLE, booking1.getCheckIn(), booking1.getCheckOut().plus(1, DAYS));

		assertThat(booking1.overlaps(booking2)).isTrue();
	}
	
	@Test
	void testOverlappingBookingsOnCheckinSideWhenCheckinIsAfter() {
		Booking booking1 = new Booking("1", "1", RoomType.SINGLE, EPOCH, EPOCH.plus(2, DAYS));
		Booking booking2 = new Booking("1", "1", RoomType.SINGLE, booking1.getCheckIn().plus(1, DAYS), booking1.getCheckOut().plus(1, DAYS));

		assertThat(booking1.overlaps(booking2)).isTrue();
	}
	
	@Test
	void testOverlappingBookingsWhenNoMatchExistsOnCheckinSide() {
		Booking booking1 = new Booking("1", "1", RoomType.SINGLE, EPOCH, EPOCH.plus(1, DAYS));
		Booking booking2 = new Booking("1", "1", RoomType.SINGLE, booking1.getCheckIn().minus(1, DAYS), booking1.getCheckIn());

		assertThat(booking1.overlaps(booking2)).isFalse();
	}
	
	@Test
	void testOverlappingBookingsWhenNoMatchExistsOnCheckoutSide() {
		Booking booking1 = new Booking("1", "1", RoomType.SINGLE, EPOCH, EPOCH.plus(1, DAYS));
		Booking booking2 = new Booking("1", "1", RoomType.SINGLE, booking1.getCheckOut(), booking1.getCheckOut().plus(1, DAYS));

		assertThat(booking1.overlaps(booking2)).isFalse();
	}

}
