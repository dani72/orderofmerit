/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.controller;

import ch.dani.oomerit.domain.Event;
import ch.dani.oomerit.service.EventService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dani
 */
@RestController
public class EventController {
    @Autowired
    private EventService service;
    
    @GetMapping( "/rest/event")
    public List<Event> findAll( Optional<Boolean> future) {
        return service.findAll( future);
    }
}
