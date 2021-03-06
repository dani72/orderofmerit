/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.Event;
import ch.dani.oomerit.service.EventService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudGrid;
import com.vaadin.flow.component.crud.CrudVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import java.util.Locale;

/**
 *
 * @author dani
 */
@Route( value = "events", layout = OrderOfMeritAppView.class)
public class EventCRUD extends AdminCRUD<Event> {
    
    private final CrudDataProvider<Event> provider;
    
    public EventCRUD( SessionMgr session, EventService service) {
        super( session, Event.class, new CrudGrid<Event>( Event.class, false), createEventEditor());
        
        this.provider = new CrudDataProvider<>( service);
        
        this.setDataProvider( this.provider);
        this.addSaveListener( this::persist);
        this.addDeleteListener( this::remove);
        
        this.getGrid().removeColumnByKey("id");
        this.getGrid().setColumns( "eventDay", "type");
        Crud.addEditColumn( this.getGrid());
        
        this.addThemeVariants(CrudVariant.NO_BORDER);
        this.setHeightFull();
    }
    
    private void persist( SaveEvent<Event> e) {
        provider.persist( e.getItem());
    }
    
    private void remove( DeleteEvent<Event> e) {
        this.provider.delete( e.getItem());
    }
    
    private static CrudEditor<Event> createEventEditor() {
        var date = new DatePicker("Date");
        var type = new ComboBox<Event.Type>( "Type", Event.Type.values());
        var form = new FormLayout( date, type);

        date.setLocale( new Locale( "ch_de"));
        type.setItems( Event.Type.values());
        
        var binder = new Binder<Event>( Event.class);
        
        binder.bind( date, Event::getEventDay, Event::setEventDay);
        binder.bind( type, Event::getType, Event::setType);

        return new BinderCrudEditor<>(binder, form);
    }
}
