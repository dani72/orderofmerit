/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.controller;

import ch.dani.oomerit.domain.Merit;
import ch.dani.oomerit.service.MeritService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dani
 */
@RestController
public class MeritController {
    
    @Autowired
    private MeritService service;
    
    @GetMapping( "/rest/merit")
    public List<Merit> findByCategory( @RequestParam String category) {
        return service.findByCategory( Merit.Category.valueOf( category));
    }
    
    @GetMapping( "/rest/merit/{id}")
    public Merit findById( @PathVariable String id) {
        return service.findById( Long.valueOf( id));
    }
}
