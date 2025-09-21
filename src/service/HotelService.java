package service;

import entity.Hotel;
import repository.interfaces.HotelRepository;
import repository.impl.HotelRepositoryImpl;
import java.util.List;

public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService() {
        this.hotelRepository = new HotelRepositoryImpl();
    }

    public Hotel createHotel(String name, String address, int totalRooms, float rating, double price) {
        if (!validateHotelInput(name, address, totalRooms, rating, price)) {
            return null;
        }

        if (hotelRepository.existsByName(name)) {
            return null;
        }

        Hotel newHotel = new Hotel(name, address, totalRooms, rating, price);
        return hotelRepository.save(newHotel);
    }

    public boolean updateHotel(String hotelId, String name, String address, double price, float rating) {
        if (!validateUpdateInput(hotelId, name, address, price, rating)) {
            return false;
        }

        Hotel existingHotel = hotelRepository.findById(hotelId);
        if (existingHotel == null) {
            return false;
        }

        if (!existingHotel.getName().equals(name) && hotelRepository.existsByName(name)) {
            return false;
        }

        Hotel updatedHotel = new Hotel(existingHotel);
        updatedHotel.setName(name);
        updatedHotel.setAddress(address);
        updatedHotel.setPrice(price);
        updatedHotel.setRating(rating);

        return hotelRepository.updateHotelDetails(hotelId, updatedHotel);
    }

    public boolean deleteHotel(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId);
        if (hotel == null) {
            return false;
        }

        if (hotel.getAvailableRooms() < hotel.getTotalRooms()) {
            return false; // Has active reservations
        }

        return hotelRepository.delete(hotelId);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public List<Hotel> getAvailableHotels(int minimumRooms) {
        return hotelRepository.findByAvailableRoomsGreaterThanEqual(minimumRooms);
    }

    public List<Hotel> getHotelsByRating(float minimumRating) {
        return hotelRepository.findByRatingGreaterThanEqual(minimumRating);
    }

    public boolean updateRoomAvailability(String hotelId, int change) {
        Hotel hotel = hotelRepository.findById(hotelId);
        if (hotel == null) {
            return false;
        }

        int newCount = hotel.getAvailableRooms() + change;
        return hotelRepository.updateAvailableRooms(hotelId, newCount);
    }

    public Hotel getHotelById(String hotelId) {
        return hotelRepository.findById(hotelId);
    }

    private boolean validateHotelInput(String name, String address, int totalRooms, float rating, double price) {
        return name != null && !name.trim().isEmpty() &&
               address != null && !address.trim().isEmpty() &&
               totalRooms > 0 &&
               rating >= 0 && rating <= 5 &&
               price > 0;
    }

    private boolean validateUpdateInput(String hotelId, String name, String address, double price, float rating) {
        return hotelId != null && !hotelId.trim().isEmpty() &&
               name != null && !name.trim().isEmpty() &&
               address != null && !address.trim().isEmpty() &&
               price > 0 &&
               rating >= 0 && rating <= 5;
    }

    public boolean checkAvailability(String hotelId, int requestedRooms) {
        Hotel hotel = hotelRepository.findById(hotelId);
        return hotel != null && hotel.getAvailableRooms() >= requestedRooms;
    }

    public double calculateTotalPrice(String hotelId, int nights, int numberOfRooms) {
        HotelService hotelService = new HotelService();
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel == null) return 0;
        return hotel.getPrice() * nights * numberOfRooms;
    }
}