import hotelkata.exception.HotelAlreadyExistingException;
import hotelkata.exception.HotelNotExistException;
import hotelkata.hotel.Hotel;
import hotelkata.hotel.HotelLayoutRepository;
import hotelkata.hotel.HotelService;
import hotelkata.hotel.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceShould {

    private static final String ROME_HOTEL_ID = "ROME_HOTEL_ID";
    private static final String NOT_EXISTING_HOTEL_ID = "NOT_EXISTING_HOTEL_ID";
    public static final String CESARE = "Cesare";
    private static final String ROOM_100 = "100";
    private static final Date DAY1 = new Date(2020, 0, 1);
    private static final Date DAY2 = new Date(2020, 0, 2);
    private HotelService hotelService;
    HotelLayoutRepository hotelLayoutRepository;

    @BeforeEach
    void setUp() {
        hotelLayoutRepository = new HotelLayoutRepository();
        hotelService = new HotelService(hotelLayoutRepository);

    }

    @Test
    void addANewHotel() {
        hotelService.addHotel(ROME_HOTEL_ID, CESARE);
        assertNotNull(hotelService.findHotelBy(ROME_HOTEL_ID));
    }

    @Test
    void throwExceptionIfHotelNotExists() {
        assertThrows(HotelNotExistException.class, () -> {
           hotelService.findHotelBy(NOT_EXISTING_HOTEL_ID);
        });
    }

    @Test
    void throwExceptionIfAddHotelTwice() {
        hotelService.addHotel(ROME_HOTEL_ID, CESARE);
        assertThrows(HotelAlreadyExistingException.class, () -> {
            hotelService.addHotel(ROME_HOTEL_ID, CESARE);;
        });
    }

    @Test
    void addNewRoom() {
        hotelService.addHotel(ROME_HOTEL_ID, CESARE);
        hotelService.setRoom(ROME_HOTEL_ID, ROOM_100, RoomType.single);

        Hotel hotel = hotelService.findHotelBy(ROME_HOTEL_ID);
        assertDoesNotThrow(() -> hotel.book(RoomType.single, DAY1, DAY2));
    }


}
