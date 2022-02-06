/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.AddMerits;
import ch.dani.oomerit.domain.Event;
import ch.dani.oomerit.domain.Merit;
import ch.dani.oomerit.domain.Player;
import ch.dani.oomerit.service.EventService;
import ch.dani.oomerit.service.MeritService;
import ch.dani.oomerit.service.OrderOfMeritService;
import ch.dani.oomerit.service.PlayerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import java.util.Collections;

/**
 *
 * @author dani
 */
public class EnterMeritsView extends VerticalLayout {
    
    public EnterMeritsView( PlayerService playerService, EventService eventService, MeritService meritService, OrderOfMeritService oomService) {
        this.setWidthFull();
        this.setHeightFull();
        
        var player = new ComboBox<Player>( "Player", playerService.findAll());
        var event = new ComboBox<Event>( "Event", eventService.findAll());
        var merits = new MultiSelectListBox<Merit>();
        
        player.setWidthFull();
        event.setWidthFull();
        merits.setWidthFull();
        
        player.setRequired( true);
        event.setRequired( true);
        
        player.setItemLabelGenerator( p -> p.getFirstname() + " " + p.getLastname());
        event.setItemLabelGenerator( e -> e.getEventDay() + " " + e.getType());
        
        player.setAllowCustomValue( false);
        event.setAllowCustomValue( false);
        
        merits.setItems( meritService.findAll());
        merits.setRenderer( new ComponentRenderer<>( m -> new Label( m.getName())));
        
        var binder = new Binder<AddMerits>();
        
        binder.bind( player, AddMerits::getPlayer, AddMerits::setPlayer);
        binder.bind( event, AddMerits::getEvent, AddMerits::setEvent);
        binder.bind( merits, AddMerits::getMerits, AddMerits::setMerits);
        
        var layout = new VerticalLayout( event, player, merits);
        
        layout.setWidthFull();
        
        var buttons = new HorizontalLayout();
        var enter = new Button( "Add Merits");
        
        buttons.add( enter);
        buttons.setAlignItems(Alignment.END);
        
        layout.add( buttons);
        
        this.add( layout);
        
        binder.setBean( new AddMerits());
        
        enter.addClickListener( e -> {
            var status = binder.validate();

            if( status.isOk()) {
                var addmerits = binder.getBean();
                
                oomService.enterMerits( addmerits);
                
                addmerits.setPlayer( null);
                addmerits.setMerits( Collections.emptySet());
                
                binder.setBean( addmerits);
                
                new Notification( "Merits created.", 2000).open();
            }
        });
    }
}
