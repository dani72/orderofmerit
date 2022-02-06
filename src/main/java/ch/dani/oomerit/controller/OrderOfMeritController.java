/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.controller;

import ch.dani.oomerit.domain.OrderEntry;
import ch.dani.oomerit.service.OrderOfMeritService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dani
 */
@RestController
public class OrderOfMeritController {
    
    @Autowired
    private OrderOfMeritService service;
    
    @GetMapping( "/rest/orderofmerit")
    public List<OrderEntry> getOrderOfMerit() {
        return service.getOrderOfMerit();
    }
}
