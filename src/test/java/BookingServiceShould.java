import hotelkata.booking.Booking;
import hotelkata.booking.BookingService;
import hotelkata.exception.HotelNotExistException;
import hotelkata.exception.NoAvailabilityException;
import hotelkata.exception.RoomTypeNotExistException;
import hotelkata.hotel.Hotel;
import hotelkata.hotel.HotelService;
import hotelkata.hotel.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceShould {
    private static final String RICCARDO_ID = "RICCARDO_ID";
    private static final String ROME_HOTEL_ID = "ROME_HOTEL_ID";
    private static final Date DAY1 = new Date(2020, 0, 1);
    private static final Date DAY2 = new Date(2020, 0, 2);
    private static final String NOT_EXISTING_HOTEL_ID = "NOT_EXISTING_HOTEL_ID";
    @Mock
    private Hotel hotel;

    @Mock
    private HotelService hotelService;
    private BookingService bookingService;


    @BeforeEach
    void setUp() {
        bookingService = new BookingService(hotelService);
    }

    @Test
    void throwExceptionIfHotelDoesNotExist() {
        when(hotelService.findHotelBy(NOT_EXISTING_HOTEL_ID)).thenThrow(HotelNotExistException.class);
        assertThrows(HotelNotExistException.class, () -> {
            bookingService.book(RICCARDO_ID,
                    NOT_EXISTING_HOTEL_ID, RoomType.single, DAY1, DAY2);
        });
    }

    @Test
    void returnBookingContainingRightDetails() {
        when(hotelService.findHotelBy(ROME_HOTEL_ID)).thenReturn(hotel);


        Booking booking = bookingService.book(RICCARDO_ID,
                ROME_HOTEL_ID, RoomType.single, DAY1, DAY2);

        assertEquals(RICCARDO_ID, booking.getEmployeeId());
        assertEquals(ROME_HOTEL_ID, booking.getHotelId());
        assertEquals(RoomType.single, booking.getRoomType());
        assertEquals(DAY1, booking.getCheckIn());
        assertEquals(DAY2, booking.getCheckOut());
    }

    @Test
    void throwExceptionIfHotelHasNotThatRoomType() {
        when(hotelService.findHotelBy(ROME_HOTEL_ID)).thenReturn(hotel);
        doThrow(RoomTypeNotExistException.class).when(hotel).book(RoomType.parisSuite, DAY1, DAY2);
        assertThrows(RoomTypeNotExistException.class, () -> {
           bookingService.book(RICCARDO_ID, ROME_HOTEL_ID, RoomType.parisSuite, DAY1, DAY2);
        });
    }

    @Test
    void bookARoom() {
        when(hotelService.findHotelBy(ROME_HOTEL_ID)).thenReturn(hotel);
        bookingService.book(RICCARDO_ID, ROME_HOTEL_ID, RoomType.single, DAY1, DAY2);
        verify(hotel).book(RoomType.single, DAY1, DAY2);
    }

    @Test
    void throwExceptionIfNotAvailable() {
        doThrow(NoAvailabilityException.class).when(hotel).book(RoomType.single, DAY1, DAY2);
        when(hotelService.findHotelBy(ROME_HOTEL_ID)).thenReturn(hotel);
        assertThrows(NoAvailabilityException.class, () -> {
            bookingService.book(RICCARDO_ID, ROME_HOTEL_ID, RoomType.single, DAY1, DAY2);
        });
    }
}
