package it.lorenzopratesi.app.hotelsbooking.controller;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import it.lorenzopratesi.app.hotelsbooking.model.Hotel;
import it.lorenzopratesi.app.hotelsbooking.model.RoomType;
import it.lorenzopratesi.app.hotelsbooking.repository.HotelRepository;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

	private static final String testHotelId1 = "1";
	private static final String testHotelName1 = "test_hotel_1";
	
	private static final String testHotelName2 = "test_hotel_2";

	@Mock
	private HotelRepository hotelRepository;

	@InjectMocks
	private HotelService hotelService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testFindHotelByIdShouldReturnTheCorrectHotel() {
		Hotel hotel = new Hotel(testHotelId1, testHotelName1);
		when(hotelRepository.findHotelById(hotel.getId())).thenReturn(hotel);
		assertThat(hotelService.findHotelBy(hotel.getId())).isEqualTo(hotel);
		verify(hotelRepository).findHotelById(hotel.getId());
	}

	@Test
	void testAddHotelWhenHotelWithSameIdDoesNotAlreadyExist() {
		Hotel newHotel = new Hotel(testHotelId1, testHotelName1);
		when(hotelRepository.findHotelById(newHotel.getId())).thenReturn(null);
		hotelService.addHotel(newHotel);
		verify(hotelRepository).addHotel(newHotel);
	}

	@Test
	void testAddHotelShouldThrowAnExeptionWhenHotelWithSameIdDoesAlreadyExist() {
		Hotel existingHotel = new Hotel(testHotelId1, testHotelName1);
		Hotel newHotel = new Hotel(testHotelId1, testHotelName2);
		when(hotelRepository.findHotelById(newHotel.getId())).thenReturn(existingHotel);

		RuntimeException e = assertThrows(RuntimeException.class, () -> hotelService.addHotel(newHotel));
		assertEquals("Hotel with id 1 already exists.", e.getMessage());
		verify(hotelRepository, Mockito.times(0)).addHotel(newHotel);
	}

	@Test
	void testSetRoomShouldThrowAnExeptionIfHotelNotExists() {
		Hotel hotel = new Hotel(testHotelId1, testHotelName2);
		when(hotelRepository.findHotelById(hotel.getId())).thenReturn(null);

		int numberOfRooms = 1;

		RuntimeException e = assertThrows(RuntimeException.class,
				() -> hotelService.setRoom(hotel, numberOfRooms, RoomType.SINGLE));
		assertEquals("Hotel with id 1 does not exists.", e.getMessage());
		verify(hotelRepository, Mockito.times(0)).setRoom(hotel, numberOfRooms, RoomType.SINGLE);
	}

	@Test
	void testSetRoomShouldInsertOneRoomOfTypeSingle() {
		Hotel existingHotel = new Hotel(testHotelId1, testHotelName1);
		Hotel hotel = new Hotel(testHotelId1, testHotelName1);
		when(hotelRepository.findHotelById(hotel.getId())).thenReturn(existingHotel);

		int numberOfRooms = 1;
		hotelService.setRoom(hotel, numberOfRooms, RoomType.SINGLE);

		Hotel updatedHotel = new Hotel(testHotelId1, testHotelName1);
		updatedHotel.setRooms(RoomType.SINGLE, numberOfRooms);
		verify(hotelRepository).setRoom(existingHotel, numberOfRooms, RoomType.SINGLE);
	}

	@Test
	void testSetRoomShouldUpdateOneRoomOfTypeSingle() {
		Hotel existingHotel = new Hotel(testHotelId1, testHotelName1);
		existingHotel.setRooms(RoomType.SINGLE, 1);

		Hotel hotel = new Hotel(testHotelId1, testHotelName1);
		hotel.setRooms(RoomType.SINGLE, 1);
		when(hotelRepository.findHotelById(hotel.getId())).thenReturn(existingHotel);

		int numberOfRooms = 2;
		hotelService.setRoom(hotel, numberOfRooms, RoomType.SINGLE);

		Hotel updatedHotel = new Hotel(testHotelId1, testHotelName1);
		updatedHotel.setRooms(RoomType.SINGLE, numberOfRooms);
		verify(hotelRepository).setRoom(existingHotel, numberOfRooms, RoomType.SINGLE);
	}

}
