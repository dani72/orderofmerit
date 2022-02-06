/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.Event;
import ch.dani.oomerit.domain.Merit;
import ch.dani.oomerit.domain.MeritsReceived;
import ch.dani.oomerit.domain.Player;
import ch.dani.oomerit.service.EventService;
import ch.dani.oomerit.service.MeritService;
import ch.dani.oomerit.service.OrderOfMeritService;
import ch.dani.oomerit.service.PlayerService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    
    private final OrderOfMeritService oomService;
    private final ComboBox<Player> players;
    private final ComboBox<Event> events;
    private final MultiSelectListBox<Merit> merits;
    private final Button saveButton;
    
    public EnterMeritsView( PlayerService playerService, EventService eventService, MeritService meritService, OrderOfMeritService oomService) {
        this.oomService = oomService;
        
        this.setWidthFull();
        this.setHeightFull();
        
        this.players = new ComboBox<>( "Player", playerService.findAll());
        this.events = new ComboBox<>( "Event", eventService.findAll());
        this.merits = new MultiSelectListBox<>();
        
        players.setWidthFull();
        events.setWidthFull();
        merits.setWidthFull();
        
        players.setRequired( true);
        events.setRequired( true);
        
        players.setItemLabelGenerator( p -> p.getFirstname() + " " + p.getLastname());
        events.setItemLabelGenerator( e -> e.getEventDay() + " " + e.getType());
        
        players.setAllowCustomValue( false);
        events.setAllowCustomValue( false);
        
        merits.setItems( meritService.findAll());
        merits.setRenderer( new ComponentRenderer<>( m -> new Label( m.getName())));
        
        var layout = new VerticalLayout( events, players, merits);
        
        layout.setWidthFull();
        
        var buttons = new HorizontalLayout();
        this.saveButton = new Button( "Update Merits");
        this.saveButton.setEnabled( false);
        
        buttons.setWidthFull();
        buttons.setAlignItems(Alignment.END);
        buttons.add( this.saveButton);
        buttons.setJustifyContentMode(JustifyContentMode.END);
        
        layout.add( buttons);
        
        this.add( layout);
        
        this.events.addValueChangeListener( this::onEventChanged);
        this.players.addValueChangeListener( this::onPlayerChanged);
        
        this.saveButton.addClickListener( this::onSave);
    }

    private void onEventChanged( ComboBox.ValueChangeEvent<Event> event) {
        var e = event.getValue();
        var p = this.players.getValue();

        updateMerits( e, p);
    }

    private void onPlayerChanged( ComboBox.ValueChangeEvent<Player> event) {
        var e = this.events.getValue();
        var p = event.getValue();

        updateMerits( e, p);
    }
    
    private void updateMerits( Event event, Player player) {
        if( event != null && player != null) {
            var mr = oomService.getMeritsReceived( event, player);
            
            this.merits.setValue( mr.getMerits());
            this.saveButton.setEnabled( true);
        }
        else {
            this.merits.setValue( Collections.emptySet());
            this.saveButton.setEnabled( false);
        }
    }

    private void onSave( ClickEvent e) {
        var player = players.getValue();
        var event = events.getValue();

        if( player != null && event != null) {
            var mr = new MeritsReceived();

            mr.setEvent( event);
            mr.setPlayer( player);
            mr.setMerits( merits.getValue());

            oomService.updateMeritsReceived( mr);

            new Notification( "Merits created.", 2000).open();
        }
    }
}
