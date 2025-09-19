package entity;

import java.util.UUID;

public class Hotel {
    private final String hotelId;
    private String name;
    private String address;
    private int availableRooms;
    private int totalRooms;
    private float rating;
    private double price;

    public Hotel(String name, String address, int totalRooms, float rating, double price) {
        this.hotelId = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.totalRooms = totalRooms;
        this.availableRooms = totalRooms;
        this.rating = rating;
        this.price = price;
    }

    public Hotel(Hotel other) {
        this.hotelId = other.hotelId;
        this.name = other.name;
        this.address = other.address;
        this.totalRooms = other.totalRooms;
        this.availableRooms = other.availableRooms;
        this.rating = other.rating;
        this.price = other.price;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        if (availableRooms >= 0 && availableRooms <= totalRooms) {
            this.availableRooms = availableRooms;
        }
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        if (rating >= 0 && rating <= 5) {
            this.rating = rating;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        }
    }

    @Override
    public boolean equals(Object objectInstant) {
        if (this == objectInstant) return true;
        if (!(objectInstant instanceof Hotel)) return false;
        Hotel hotel = (Hotel) objectInstant;
        return hotelId.equals(hotel.hotelId);
    }

    @Override
    public int hashCode() {
        return hotelId.hashCode();
    }
}