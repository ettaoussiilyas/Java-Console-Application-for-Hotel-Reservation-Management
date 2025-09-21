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
    /**
     * Updates the details of a hotel, including available rooms.
     * @param hotelId The ID of the hotel to update.
     * @param updatedHotel The hotel object with updated details.
     * @return true if update is successful, false otherwise.
     */
    boolean updateHotelDetails(String hotelId, entity.Hotel updatedHotel);
}