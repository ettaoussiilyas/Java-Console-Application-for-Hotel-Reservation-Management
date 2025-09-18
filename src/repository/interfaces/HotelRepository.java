package repository.interfaces;

import entity.Hotel;
import java.util.List;

public interface HotelRepository {

    Hotel save(Hotel hotel);
    Hotel findById(String hotelId);
    List<Hotel> findAll();
    List<Hotel> findByAvailableRooms(int rooms);
    List<Hotel> findByRating(float rating);
    boolean delete(String hotelId);
    boolean exists(String hotelId);
    // Business logic operations
    boolean hasActiveReservation(String hotelId);
    boolean updateRoomCount(String hotelId, int change);
    boolean updateHotelInfo(String hotelId, String name, String address);
    boolean isNameUnique(String name);
    int getAvailableRooms(String hotelId);
}
