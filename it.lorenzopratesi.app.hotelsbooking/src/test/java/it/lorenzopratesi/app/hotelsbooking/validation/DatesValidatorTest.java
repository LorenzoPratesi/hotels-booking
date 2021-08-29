package it.lorenzopratesi.app.hotelsbooking.validation;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatesValidatorTest {

	private DatesValidator datesValidator;
	private static final LocalDate EPOCH = LocalDate.of(1970, 1, 1);

	@BeforeEach
	void setup() {
		datesValidator = new DatesValidator();
	}

	@Test
	void testReturnsFalseIfCheckInDateIsSameAsCheckOutDate() {
		LocalDate checkIn = EPOCH;
		LocalDate checkOut = EPOCH;
		assertThat(datesValidator.validate(checkIn, checkOut)).isFalse();
	}
	
	@Test
    void testReturnsFalseIfCheckInDateIsAfterCheckOutDate() {
		LocalDate checkIn = EPOCH.plus(1, DAYS);
		LocalDate checkOut = EPOCH;
		assertThat(datesValidator.validate(checkIn, checkOut)).isFalse();
    }
	
	@Test
    void testReturnsTrueIfCheckInDateIsBeforeCheckOutDate() {
		LocalDate checkIn = EPOCH.minus(1, DAYS);
		LocalDate checkOut = EPOCH;
		assertThat(datesValidator.validate(checkIn, checkOut)).isTrue();
    }

}
