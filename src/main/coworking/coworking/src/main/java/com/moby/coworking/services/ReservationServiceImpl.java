package com.moby.coworking.services;

import com.moby.coworking.dtos.ReservationRequestDto;
import com.moby.coworking.dtos.ReservationResponseDto;
import com.moby.coworking.exceptions.ReservationConflictException;
import com.moby.coworking.mappers.ReservationMapper;
import com.moby.coworking.models.Reservation;
import com.moby.coworking.models.ReservationStatus;
import com.moby.coworking.models.Room;
import com.moby.coworking.models.User;
import com.moby.coworking.repositories.ReservationRepository;
import com.moby.coworking.repositories.RoomRepository;
import com.moby.coworking.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto dto) {
        Room room = roomRepository.findById(dto.getRoomId()).orElseThrow(()-> new IllegalArgumentException("Sala no encontrada") );

        if (!room.isEnabled()){
            throw new IllegalArgumentException("Sala deshabilitada");

        }

        User user = userRepository.findById(dto.getUserId()).orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));

        List<Reservation> conflicts = reservationRepository
                .findReservationByData(room, ReservationStatus.ACTIVE,dto.getEndDateTime(),dto.getStartDatetime());

        if(!conflicts.isEmpty()){
            throw new ReservationConflictException("La sala ya esta reservada para el rango de horario solicitado");
        }

        Reservation reservation = Reservation.builder()
                .room(room)
                .user(user)
                .startDateTime(dto.getStartDatetime())
                .endDateTime(dto.getEndDateTime())
                .status(ReservationStatus.ACTIVE)
                .build();

        Reservation saved = reservationRepository.save(reservation);

        return new ReservationResponseDto(
                saved.getId(),
                saved.getUser().getId(),
                saved.getRoom().getId(),
                saved.getStartDateTime(),
                saved.getEndDateTime(),
                saved.getStatus().name()
        );

    }

    @Override
    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream().map(ReservationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDto> getReservationByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado"));

        return reservationRepository.findByUserId(user.getId()).stream().map(ReservationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void cancelReservation(Long reservationId, Long currentUserId, boolean isAdmin) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new IllegalArgumentException("Reserva no encontrada"));

        if(!isAdmin && !reservation.getUser().getId().equals(currentUserId)){
            throw new SecurityException("No tienes permitido cancelar esta reserva");
        }
        if(reservation.getStatus() == ReservationStatus.CANCELLED){
            return;
        }
    }
}
