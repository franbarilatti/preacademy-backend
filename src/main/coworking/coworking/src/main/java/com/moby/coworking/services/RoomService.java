package com.moby.coworking.services;

import com.moby.coworking.dtos.RoomDTO;
import com.moby.coworking.models.Room;
import com.moby.coworking.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room createRoom(RoomDTO roomDTO){

        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setLocation(room.getLocation());
        room.setEnabled(true);

        return roomRepository.save(room);

    }

    public List<Room> getAll(){
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms(){
        return roomRepository.findByEnabledTrue();
    }

    public Room updateRoom(Long id, RoomDTO roomDTO){
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setLocation(room.getLocation());

        return roomRepository.save(room);

    }

    public void disableRoom(Long id){
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        room.setEnabled(false);
        roomRepository.save(room);

    }

}
