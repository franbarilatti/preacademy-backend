package com.moby.coworking.services;

import com.moby.coworking.dtos.RoomDTO;
import com.moby.coworking.models.Room;
import com.moby.coworking.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;


    @Test
    void testCreateRoom(){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("Sala A");
        roomDTO.setCapacity(6);
        roomDTO.setLocation("1er Piso");
        roomDTO.setEnabled(true);

        Room room = new Room();
        room.setName("Sala A");
        room.setCapacity(6);
        room.setLocation("Piso 1");
        room.setEnabled(true);

        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Room result = roomService.createRoom(roomDTO);

        assertEquals("Sala A", result.getName());
        assertEquals(6, result.getCapacity());

    }

    @Test
    void testUpdateRoom(){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("Sala B");
        roomDTO.setCapacity(15);
        roomDTO.setLocation("Piso 2");

        Room existingRoom = new Room();
        existingRoom.setId(1L);
        existingRoom.setName("Sala A");
        existingRoom.setCapacity(6);
        existingRoom.setLocation("Piso 1");
        existingRoom.setEnabled(true);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(existingRoom));

        when(roomRepository.save(any(Room.class))).thenReturn(existingRoom);

        Room result = roomService.updateRoom(1L, roomDTO);
        assertEquals("Sala B", result.getName());
        assertEquals(15, result.getCapacity());


    }


}
