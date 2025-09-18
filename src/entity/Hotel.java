package entity;

import java.util.UUID;

public class Hotel {

    private String name;
    private String address;
    private String hotelId;
    private int availableRooms;
    private float rating;
    private double price;


    public Hotel(String name, String address, int availableRooms, float rating, double price) {
        this.name = name;
        this.address = address;
        this.hotelId = UUID.randomUUID().toString();
        this.availableRooms = availableRooms;
        this.rating = rating;
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getIdHotel() {
        return hotelId;
    }

    public float getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIdHotel(String idHotel) {
        this.hotelId = idHotel;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
