import hotelkata.exception.NoAvailabilityException;
import hotelkata.hotel.Hotel;
import hotelkata.hotel.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class HotelShould {

    public static final String ANY_HOTEL_NAME = "anyHotelName";
    public static final String ANY_ID = "anyId";
    private static final Date DAY1 = new Date(2020, 0, 1);
    private static final Date DAY2 = new Date(2020, 0, 2);
    private static final Date DAY3 = new Date(2020, 0, 3);
    private static final Date DAY4 = new Date(2020, 0, 4);
    public static final String SINGLE_ROOM_1 = "Single Room 1";
    private static final String SINGLE_ROOM_2 = "Single Room 2";

    @Test
    void bookOneRoom() {
        Hotel hotel = new Hotel(ANY_ID, ANY_HOTEL_NAME);

        hotel.setRoom(SINGLE_ROOM_1, RoomType.single);
        assertDoesNotThrow(() -> hotel.book(RoomType.single, DAY1, DAY2));
    }

    @Test
    void NotBookRoomTwiceAtSameDay() {
        Hotel hotel = new Hotel(ANY_ID, ANY_HOTEL_NAME);
        hotel.setRoom(SINGLE_ROOM_1, RoomType.single);

        hotel.book(RoomType.single, DAY1, DAY2);
        assertThrows(NoAvailabilityException.class, () ->
                hotel.book(RoomType.single, DAY1, DAY2));
    }

    @Test
    void BookRoomAtADifferentDay() {
        Hotel hotel = new Hotel(ANY_ID, ANY_HOTEL_NAME);
        hotel.setRoom(SINGLE_ROOM_1, RoomType.single);

        hotel.book(RoomType.single, DAY1, DAY2);
        assertDoesNotThrow(() -> hotel.book(RoomType.single, DAY3, DAY4));
    }

    @Test
    void notBookRoomWithinABookedPeriod() {
        Hotel hotel = new Hotel(ANY_ID, ANY_HOTEL_NAME);
        hotel.setRoom(SINGLE_ROOM_1, RoomType.single);

        hotel.book(RoomType.single, DAY1, DAY3);
        assertThrows(NoAvailabilityException.class, () ->
                hotel.book(RoomType.single, DAY2, DAY3));
    }

    @Test
    void bookTwoRoomsOnSamePeriod() {
        Hotel hotel = new Hotel(ANY_ID, ANY_HOTEL_NAME);
        hotel.setRoom(SINGLE_ROOM_1, RoomType.single);
        hotel.setRoom(SINGLE_ROOM_2, RoomType.single);

        hotel.book(RoomType.single, DAY1, DAY2);
        assertDoesNotThrow(() -> hotel.book(RoomType.single, DAY1, DAY2));
    }
}
