package hotelkata.booking;

import hotelkata.hotel.Hotel;
import hotelkata.hotel.HotelService;
import hotelkata.hotel.RoomType;

import java.util.Date;

public class BookingService {
    private HotelService hotelService;

    public BookingService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // Collaborators (?)

    public Booking book(String employeeId, String hotelId, RoomType roomType, Date checkIn, Date checkOut) {
        Hotel hotel = hotelService.findHotelBy(hotelId);
        hotel.book(roomType, checkIn, checkOut);
        Booking booking = new Booking(employeeId, hotelId, roomType, checkIn, checkOut);
        return booking;
    }

}

