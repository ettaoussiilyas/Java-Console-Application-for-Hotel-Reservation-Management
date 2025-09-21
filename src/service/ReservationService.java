package service;

import entity.Reservation;
import entity.Hotel;
import service.HotelService;
import repository.interfaces.ReservationRepository;
import repository.interfaces.HotelRepository;
import repository.impl.ReservationRepositoryImpl;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {


    private final ReservationRepository reservationRepository;
    private final HotelService hotelService;
    public ReservationService() {

        this.reservationRepository = new ReservationRepositoryImpl();
        this.hotelService = new HotelService();
    }

    public Reservation createReservation(String reservationId, String hotelId, String customerId,
                                         String checkInDate, String checkOutDate, int numberOfRooms, double totalPrice, String reservationStatus) {
        if (!validateReservationInput(hotelId, customerId, checkInDate, checkOutDate, numberOfRooms)) {
            return null;
        }

        // Check hotel availability and update room count
        if (!hotelService.updateRoomAvailability(hotelId, -numberOfRooms)) {
            return null;
        }

        Reservation newReservation = new Reservation(reservationId, hotelId, customerId,
                checkInDate, checkOutDate, numberOfRooms, totalPrice, reservationStatus);
        return reservationRepository.save(newReservation);
    }


    public boolean validateReservationInput(String hotelId, String customerId, String checkInDate, String checkoutDate, int numberOfRooms){
        if(hotelId == null || customerId == null || checkInDate == null || checkoutDate == null || numberOfRooms <=0){
            return false;
        }
        if(checkInDate.compareTo(checkoutDate) >= 0){
            return false;
        }
        return true;
    }


    public boolean updateReservationStatus(String reservationId, String newStatus){
        if (newStatus == null || newStatus.trim().isEmpty()){
            return false;
        }
        Reservation reservation = reservationRepository.findById(reservationId);
        if(reservation == null){
            return false;
        }
        if(reservation.getReservationStatus().equals(newStatus)){
            return false;
        }
        return reservationRepository.updateReservationStatus(reservationId, newStatus);
    }

    public boolean deleteReservation(String reservationId){
        Reservation reservation = reservationRepository.findById(reservationId);
        if(reservation == null){
            return false;
        }
        return reservationRepository.delete(reservationId);
    }

    public List<Reservation> getAllRservations(){
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByHotelId(String hotelId){
        return reservationRepository.findByHotelId(hotelId);
    }

    public boolean cancelReservation(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null || !reservation.getReservationStatus().equals("Confirmed")) {
            return false;
        }

        // Return rooms to hotel availability
        if (!hotelService.updateRoomAvailability(reservation.getHotelId(), reservation.getNumberOfRooms())) {
            return false;
        }

        reservation.setReservationStatus("Cancelled");
        return reservationRepository.updateReservationStatus(reservationId, "Cancelled");
    }

    public List<Reservation> getReservationsByCustomerId(String customerId){
        return reservationRepository.findByCustomerId(customerId);
    }

    public Reservation findById(String reservationId) {
        if (reservationId == null || reservationId.trim().isEmpty()) {
            return null;
        }
        return reservationRepository.findById(reservationId);
    }

    public List<Reservation> getReservationHistory(String customerId) {
        return reservationRepository.findByCustomerId(customerId)
                .stream()
                .sorted((r1, r2) -> r2.getCheckInDate().compareTo(r1.getCheckInDate()))
                .collect(Collectors.toList());
    }

    public double calculateTotalPrice(String hotelId, int nights, int numberOfRooms) {
        HotelService hotelService = new HotelService();
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel == null) return 0;
        return hotel.getPrice() * nights * numberOfRooms;
    }


}