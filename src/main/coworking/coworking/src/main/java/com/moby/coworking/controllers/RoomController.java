package com.moby.coworking.controllers;


import com.moby.coworking.dtos.RoomDTO;
import com.moby.coworking.models.Room;
import com.moby.coworking.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor 
public class RoomController {

    private final RoomService roomService;


    @PostMapping
    public ResponseEntity<Room> createRoom(@Valid @RequestBody RoomDTO roomDTO){
        return ResponseEntity.ok(roomService.createRoom(roomDTO));
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAll(){
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAvailableRooms(){
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDTO roomDTO){
        roomService.disableRoom(id);
        return ResponseEntity.ok().build();
    }



}
