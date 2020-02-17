import hotelkata.booking.Booking;
import hotelkata.booking.BookingService;
import hotelkata.exception.NoAvailabilityException;
import hotelkata.hotel.HotelLayoutRepository;
import hotelkata.hotel.HotelService;
import hotelkata.hotel.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AcceptanceTest {

    private static final String RICCARDO_ID = "RICCARDO_ID";
    private static final String ROME_HOTEL_ID = "ROME_HOTEL_ID";
    private static final Date DAY1 = new Date(2020, 0, 1);
    private static final Date DAY2 = new Date(2020, 0, 2);
    public static final String ROOM_100 = "100";
    private BookingService bookingService;
    private HotelService hotelService;
    private HotelLayoutRepository hotelLayoutRepository;

    @BeforeEach
    void setUp() {
        hotelLayoutRepository = new HotelLayoutRepository();
    }

    @Test
    void employeeCanBookARoom() {
        hotelService = new HotelService(hotelLayoutRepository);
        hotelService.addHotel(ROME_HOTEL_ID, "Cesare");
        hotelService.setRoom(ROME_HOTEL_ID, ROOM_100, RoomType.single);
        bookingService = new BookingService(hotelService);
        Booking booking = bookingService.book(RICCARDO_ID,
                ROME_HOTEL_ID, RoomType.single, DAY1, DAY2);
        assertNotNull(booking);
    }

    @Test
    void checkAvailability() {
        hotelService = new HotelService(hotelLayoutRepository);
        hotelService.addHotel(ROME_HOTEL_ID, "Cesare");
        hotelService.setRoom(ROME_HOTEL_ID, ROOM_100, RoomType.single);
        bookingService = new BookingService(hotelService);

        bookingService.book(RICCARDO_ID,
                ROME_HOTEL_ID, RoomType.single, DAY1, DAY2);

        assertThrows(NoAvailabilityException.class, () -> {
            bookingService.book(RICCARDO_ID,
                    ROME_HOTEL_ID, RoomType.single, DAY1, DAY2);
        });

    }


}
