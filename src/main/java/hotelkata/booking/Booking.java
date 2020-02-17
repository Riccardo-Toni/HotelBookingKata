package hotelkata.booking;

import hotelkata.hotel.RoomType;

import java.util.Date;

public class Booking {
    private final String employeeId;
    private final String hotelId;
    private final RoomType roomType;
    private final Date checkIn;
    private final Date checkOut;

    public Booking(String employeeId, String hotelId, RoomType roomType, Date checkIn, Date checkOut) {
        this.employeeId = employeeId;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }
}
