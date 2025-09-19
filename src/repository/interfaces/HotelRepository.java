package repository.interfaces;

import entity.Hotel;
import java.util.List;

public interface HotelRepository {

    Hotel save(Hotel hotel);
    Hotel findById(String hotelId);
    List<Hotel> findAll();
    boolean delete(String hotelId);
    
    List<Hotel> findByAvailableRoomsGreaterThanEqual(int rooms);
    List<Hotel> findByRatingGreaterThanEqual(float rating);
    Hotel findByName(String name);
    boolean existsById(String hotelId);
    boolean existsByName(String name);
    
    boolean updateAvailableRooms(String hotelId, int newCount);
    boolean updateHotelDetails(String hotelId, Hotel updatedHotel);
}