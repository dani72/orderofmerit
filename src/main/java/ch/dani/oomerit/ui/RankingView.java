/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.OrderEntry;
import ch.dani.oomerit.service.OrderOfMeritService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

/**
 *
 * @author dani
 */
@Route( value = "ranking", layout = OrderOfMeritAppView.class)
public class RankingView extends VerticalLayout {
    
    private final OrderOfMeritService service;
    
    public RankingView( OrderOfMeritService service) {
        this.service = service;
        
        this.setHeightFull();
        this.setWidthFull();

        VirtualList vl = new VirtualList();
        
        vl.setItems( service.getOrderOfMerit());
        vl.setRenderer( new ComponentRenderer<>( this::renderComponent));
        vl.setWidthFull();
        vl.setHeightFull();
        
        this.add( vl);
    }
    
    private OrderEntryItem renderComponent( OrderEntry entry) {
        return new OrderEntryItem( entry);
    }
}
