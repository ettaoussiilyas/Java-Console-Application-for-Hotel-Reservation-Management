package repository.impl;

import entity.Hotel;
import repository.interfaces.HotelRepository;
import java.util.*;
import java.util.stream.Collectors;

public class HotelRepositoryImpl implements HotelRepository {
    private final Map<String, Hotel> hotels = new HashMap<>();

    @Override
    public Hotel save(Hotel hotel) {
        hotels.put(hotel.getHotelId(), hotel);
        return hotel;
    }

    @Override
    public Hotel findById(String hotelId) {
        return hotels.get(hotelId);
    }

    @Override
    public List<Hotel> findAll() {
        return new ArrayList<>(hotels.values());
    }

    @Override
    public boolean delete(String hotelId) {
        return hotels.remove(hotelId) != null;
    }

    @Override
    public List<Hotel> findByAvailableRoomsGreaterThanEqual(int rooms) {
        return hotels.values().stream()
                .filter(hotel -> hotel.getAvailableRooms() >= rooms)
                .collect(Collectors.toList());
    }

    @Override
    public List<Hotel> findByRatingGreaterThanEqual(float rating) {
        return hotels.values().stream()
                .filter(hotel -> hotel.getRating() >= rating)
                .collect(Collectors.toList());
    }

    @Override
    public Hotel findByName(String name) {
        return hotels.values().stream()
                .filter(hotel -> hotel.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean existsById(String hotelId) {
        return hotels.containsKey(hotelId);
    }

    @Override
    public boolean existsByName(String name) {
        return hotels.values().stream()
                .anyMatch(hotel -> hotel.getName().equalsIgnoreCase(name));
    }

    @Override
    public boolean updateAvailableRooms(String hotelId, int newCount) {
        Hotel hotel = hotels.get(hotelId);
        if (hotel != null && newCount >= 0 && newCount <= hotel.getTotalRooms()) {
            hotel.setAvailableRooms(newCount);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateHotelDetails(String hotelId, Hotel updatedHotel) {
        if (!hotels.containsKey(hotelId)) {
            return false;
        }
        hotels.put(hotelId, updatedHotel);
        return true;
    }
}