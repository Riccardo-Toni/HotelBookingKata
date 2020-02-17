package hotelkata.hotel;

import hotelkata.exception.NoAvailabilityException;
import hotelkata.exception.RoomTypeNotExistException;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Hotel {
    private final String hotelId;
    private final String hotelName;
    private Map<String, RoomType> rooms = new HashMap<>();
    private Map<LocalDate, Integer> booked = new HashMap<>();
//    private Map<String, Set<Date>>

    public Hotel(String hotelId, String hotelName) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
    }

    private boolean hasRoomType(RoomType roomType) {
        return rooms.containsValue(roomType);
    }


    public void setRoom(String roomNumber, RoomType roomType) {
        rooms.put(roomNumber, roomType);

    }

    public void book(RoomType roomType, Date checkInDate, Date checkOutDate) {
        LocalDate checkInLocal = LocalDate.of(checkInDate.getYear(),
                                                checkInDate.getMonth() +1,
                                                checkInDate.getDate());
        LocalDate checkOutLocal = LocalDate.of(checkOutDate.getYear(),
                                                checkOutDate.getMonth() +1,
                                                checkOutDate.getDate());
        if(!hasRoomType(roomType)){
            throw new RoomTypeNotExistException();
        }
        if(checkAvailabilityForDates(checkInLocal, checkOutLocal)){
            throw new NoAvailabilityException();
        } else {
            checkInLocal.datesUntil(checkOutLocal).forEach((dateToBook) ->
                    booked.put(dateToBook, increaseBookedRoomNumber(dateToBook)));

        }
    }

    private Integer increaseBookedRoomNumber(LocalDate dateToBook) {
        Integer alreadyBooked = booked.get(dateToBook);
        if(alreadyBooked == null) {
            return 1;
        }else{
            return alreadyBooked++;
        }
    }

    private boolean checkAvailabilityForDates(LocalDate checkInLocal, LocalDate checkOutLocal) {
        int totalRoomsOfType = getTotalRoomsOfType();
        return checkInLocal.datesUntil(checkOutLocal).anyMatch((dateToBook) ->
                booked.get(dateToBook)!=null && booked.get(dateToBook).equals(totalRoomsOfType));
    }

    private int getTotalRoomsOfType() {
        return rooms.size();
    }
}

/*
1. check
2. getAvailRooms for that period. getAvailCount for that period.
3. pick first room
4. book that room for that period



 */