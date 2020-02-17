package hotelkata.hotel;

import hotelkata.exception.HotelAlreadyExistingException;

import java.util.HashMap;
import java.util.Map;

public class HotelLayoutRepository {

    Map<String, Hotel> hotelMap = new HashMap<>();

    public Hotel getHotelLayoutBy(String hotelId) {
        return hotelMap.get(hotelId);
    }

    public void addHotel(String hotelId, String hotelName) {
        if (hotelMap.get(hotelId) != null) {
            throw new HotelAlreadyExistingException();
        }
        Hotel hotel = new Hotel(hotelId, hotelName);
        hotelMap.put(hotelId, hotel);

    }
}
