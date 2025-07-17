package com.moby.coworking.repositories;

import com.moby.coworking.models.Reservation;
import com.moby.coworking.models.ReservationStatus;
import com.moby.coworking.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findByUserId(Long id);

    List<Reservation> findReservationByData(Room room, ReservationStatus status, LocalDateTime endDateTime, LocalDateTime startDateTime);

}
