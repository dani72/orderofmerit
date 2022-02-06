/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.controller;

import ch.dani.oomerit.domain.Player;
import ch.dani.oomerit.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dani
 */
@RestController
public class PlayerController {
    
    @Autowired
    private PlayerService service;
    
    @GetMapping( "/rest/player/{id}")
    public Player findPlayer( @PathVariable String id) {
        return service.findById( Long.valueOf( id));
    }
}
