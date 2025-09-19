package repository.impl;

import java.util.*;
import entity.Reservation;
import repository.interfaces.ReservationRepository;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepository {

    private final Map<String, Reservation> reservations = new HashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
        return reservation;
    }

    @Override
    public Reservation findById(String reservationId) {
        return reservations.get(reservationId);
    }

    @Override
    public List<Reservation> findAll(){
        return new ArrayList<>(reservations.values());
    }

    @Override
    public boolean delete(String reservationId) {
        return reservations.remove(reservationId) != null;
    }

    @Override
    public List<Reservation> findByHotelId(String hotelId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getHotelId().equals(hotelId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByCustomerId(String customerId){
        return reservations.values().stream()
                .filter(reservation -> reservation.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateReservationStatus(String reservationId, String newStatus){
        Reservation reservation = reservations.get(reservationId);
        if(reservation != null && newStatus != null && !newStatus.trim().isEmpty()){
            reservation.setReservationStatus(newStatus);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReservationDetails(String reservationId, Reservation updatedReservation){
        Reservation existingReservation = reservations.get(reservationId);
        if(existingReservation != null){
            existingReservation.setHotelId(updatedReservation.getHotelId());
            existingReservation.setCustomerId(updatedReservation.getCustomerId());
            existingReservation.setCheckInDate(updatedReservation.getCheckInDate());
            existingReservation.setCheckOutDate(updatedReservation.getCheckOutDate());
            existingReservation.setReservationStatus(updatedReservation.getReservationStatus());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReservationRooms(String reservationId, int newCount){
        Reservation reservation = reservations.get(reservationId);
        if(reservation != null && newCount >=0 && newCount <= reservation.getNumberOfRooms()){
            reservation.setNumberOfRooms(newCount);
            return true;
        }
        return false;
    }



}
