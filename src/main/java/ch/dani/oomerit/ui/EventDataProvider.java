/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.Event;
import ch.dani.oomerit.service.EventService;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import java.util.stream.Stream;

/**
 *
 * @author dani
 */
public class EventDataProvider extends AbstractBackEndDataProvider<Event, CrudFilter> {

    private final EventService service;

    public EventDataProvider(  EventService service) {
        this.service = service;
    }
    
    @Override
    protected Stream<Event> fetchFromBackEnd(Query<Event, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        return service.findAll().stream().skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<Event, CrudFilter> query) {
        return service.findAll().size();
    }

    public void persist(Event event) {
        service.save( event);
    }

    public void delete(Event event) {
        service.remove( event);
    }
}
