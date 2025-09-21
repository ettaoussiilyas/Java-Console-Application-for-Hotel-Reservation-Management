package entity;

import java.util.UUID;

public class Reservation {

    private String reservationId;
    private String hotelId;
    private String customerId;
    private String checkInDate;
    private String checkOutDate;
    private int numberOfRooms;
    private double totalPrice;
    private String reservationStatus;

    public Reservation(String reservationId, String hotelId, String customerId, String checkInDate, 
                  String checkOutDate, int numberOfRooms, double totalPrice, String reservationStatus) {
    this.reservationId = reservationId;
    this.hotelId = hotelId;
    this.customerId = customerId;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.numberOfRooms = numberOfRooms;
    this.totalPrice = totalPrice;
    this.reservationStatus = reservationStatus;
}

    public Reservation(Reservation other) {
        this.reservationId = other.reservationId;
        this.hotelId = other.hotelId;
        this.customerId = other.customerId;
        this.checkInDate = other.checkInDate;
        this.checkOutDate = other.checkOutDate;
        this.numberOfRooms = other.numberOfRooms;
        this.totalPrice = other.totalPrice;
        this.reservationStatus = other.reservationStatus;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    // let's create setters
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }


    public double getPrice() {
        return totalPrice;
    }

    public boolean isValidReservation() {
        return numberOfRooms > 0 &&
                checkInDate != null &&
                checkOutDate != null &&
                checkInDate.compareTo(checkOutDate) < 0;
    }

}