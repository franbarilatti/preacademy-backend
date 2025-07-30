package com.moby.coworking.services;

import com.moby.coworking.dtos.ReservationDTO;
import com.moby.coworking.models.Reservation;
import com.moby.coworking.models.ReservationStatus;
import com.moby.coworking.models.Room;
import com.moby.coworking.models.User;
import com.moby.coworking.repositories.ReservationRepository;
import com.moby.coworking.repositories.RoomRepository;
import com.moby.coworking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public Reservation createReservation(ReservationDTO  reservationDTO){
        Room room = roomRepository.findById(reservationDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Reservation> collisions = reservationRepository
                .findReservationByData(room.getId(), ReservationStatus.ACTIVE, reservationDTO.getEndDateTime(), reservationDTO.getStartDateTime());

        if (!collisions.isEmpty()){
            throw new RuntimeException("El horario de reserva no está disponible");

        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setStartDateTime(reservationDTO.getStartDateTime());
        reservation.setEndDateTime(reservationDTO.getEndDateTime());
        reservation.setStatus(ReservationStatus.ACTIVE);

        return reservationRepository.save(reservation);

    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationByUser(Long userId){
        return reservationRepository.findByUserId(userId);
    }

    public void cancelReservation(Long id){
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    public boolean isReservationOwner(Long reservationId, String username){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return reservation.getUser().getEmail().equals(username);
    }



}
