package hotelkata.hotel;

import hotelkata.exception.HotelNotExistException;

public class HotelService {

    HotelLayoutRepository hotelLayoutRepository;

    public HotelService(HotelLayoutRepository hotelLayoutRepository) {
        this.hotelLayoutRepository = hotelLayoutRepository;
    }

    public Hotel findHotelBy(String hotelId) {
        Hotel hotel = hotelLayoutRepository.getHotelLayoutBy(hotelId);
        if(hotel ==  null){
            throw new HotelNotExistException();
        }
        return hotel;
    }

    public void addHotel(String hotelId, String hotelName) {
        hotelLayoutRepository.addHotel(hotelId, hotelName);

    }

    public void setRoom(String hotelId, String roomNumber, RoomType roomType) {
        Hotel hotel = findHotelBy(hotelId);
        hotel.setRoom(roomNumber, roomType);
    }
}
