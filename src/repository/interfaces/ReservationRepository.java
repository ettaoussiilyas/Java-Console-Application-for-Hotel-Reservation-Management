package repository.interfaces;

import entity.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);
    Reservation findById(String reservationId);
    List<Reservation> findAll();
    boolean delete(String reservationId);

    List<Reservation> findByHotelId(String hotelId);
    List<Reservation> findByCustomerId(String customerId);
//    List<Reservation> findByCheckInDate(String checkInDate);
//    List<Reservation> findByCheckOutDate(String checkOutDate);
//    List<Reservation> findByReservationStatus(String reservationStatus);

    boolean updateReservationStatus(String reservationId, String newStatus);
    boolean updateReservationDetails(String reservationId, Reservation updatedReservation);
    boolean updateReservationRooms(String reservationId, int newCount);
}
