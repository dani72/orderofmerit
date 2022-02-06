/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.OrderEntry;
import ch.dani.oomerit.service.OrderOfMeritService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 *
 * @author dani
 */
public class RankingView extends VerticalLayout {
    
    private final OrderOfMeritService service;
    
    public RankingView( OrderOfMeritService service) {
        this.service = service;
        
        this.setHeightFull();
        this.setWidthFull();

        Grid<OrderEntry> grid = new Grid<>();
        
        grid.addColumn( e -> e.getPlayer().getFirstname()).setHeader( "First Name");
        grid.addColumn( e -> e.getPlayer().getLastname()).setHeader( "Last Name");
        grid.addColumn( e -> e.getPlayer().getNickname()).setHeader( "Nick Name");
        grid.addColumn( e -> e.getNofMerits()).setHeader( "Merits");
        
        grid.setItems( service.getOrderOfMerit());
        grid.setHeightFull();
        grid.setWidthFull();
        
        this.add( grid);
    }
}
