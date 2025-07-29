package com.moby.coworking.services;

import com.moby.coworking.dtos.ReservationDTO;
import com.moby.coworking.models.Reservation;
import com.moby.coworking.models.ReservationStatus;
import com.moby.coworking.models.Room;
import com.moby.coworking.models.User;
import com.moby.coworking.repositories.ReservationRepository;
import com.moby.coworking.repositories.RoomRepository;
import com.moby.coworking.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private ReservationService reservationService;


    @Test
    void testCreateReservation(){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(1L);
        reservationDTO.setRoomId(1L);
        reservationDTO.setStartDateTime(LocalDateTime.now());
        reservationDTO.setEndDateTime(LocalDateTime.now().plusHours(1));

        User user = new User();
        user.setId(1L);

        Room room = new Room();
        room.setId(1L);
        room.setEnabled(true);

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setStatus(ReservationStatus.ACTIVE);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(reservationRepository.findReservationByData(any(),any(),any(),any())).thenReturn(Collections.emptyList());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.createReservation(reservationDTO);
        assertEquals(ReservationStatus.ACTIVE,result.getStatus());


    }

    @Test
    void testCreateReservationCollision(){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(1L);
        reservationDTO.setRoomId(1L);
        reservationDTO.setStartDateTime(LocalDateTime.now());
        reservationDTO.setEndDateTime(LocalDateTime.now().plusHours(1));

        User user = new User();
        user.setId(1L);

        Room room = new Room();
        room.setId(1L);
        room.setEnabled(true);

        Reservation existingReservation = new Reservation();
        existingReservation.setId(2L);
        existingReservation.setStatus(ReservationStatus.ACTIVE);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(reservationRepository.findReservationByData(any(),any(),any(),any())).thenReturn(Collections.singletonList(existingReservation));

        assertThrows(RuntimeException.class, () -> reservationService.createReservation(reservationDTO));

    }



}
