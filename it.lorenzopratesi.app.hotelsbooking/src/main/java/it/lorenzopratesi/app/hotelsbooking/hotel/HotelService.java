package it.lorenzopratesi.app.hotelsbooking.hotel;

public class HotelService {

	private HotelRepository hotelRepository;

	public HotelService(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	public void addHotel(Hotel hotel) {
		Hotel existingProduct = hotelRepository.findHotelById(hotel.getId());
		if (existingProduct != null) {
			throw new RuntimeException("Hotel with id " + hotel.getId() + " already exists.");
		}
		hotelRepository.addHotel(hotel);
	}

	public void setRoom(Hotel hotel, int number, RoomType roomType) {
		Hotel existingProduct = hotelRepository.findHotelById(hotel.getId());
		if (existingProduct == null) {
			throw new RuntimeException("Hotel with id " + hotel.getId() + " does not exists.");
		}
		hotelRepository.setRoom(existingProduct, number, roomType);
	}

}
