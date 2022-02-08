/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.service;

import ch.dani.oomerit.domain.Event;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author dani
 */
@Service
public class EventService implements CrudService<Event> {
 
    @Autowired
    private JdbcTemplate template;
    
    @Override
    public List<Event> findAll() {
        return template.query( "SELECT * FROM event", this::createEvent);
    }
    
    @Override
    public List<Event> findAll( List<SortField> sortFields) {
        return findAll();
    }

    public List<Event> findAll( Optional<Boolean> future) {
        Date date = future.map( f -> new Date( f ? System.currentTimeMillis() : 0)).orElse( new Date( 0));
        
        return template.query( "SELECT * FROM event where event.training_date >= ?", this::createEvent, date);
    }
    
    @Override
    public void save( Event event) {
        if( event.getId() == null) {
            insert( event);
        }
        else {
            update( event);
        }
    }
    
    @Override
    public void remove( Event event) {
        template.update( "DELETE FROM event WHERE event_id = ?", event.getId());
    }
    
    private void insert( Event event) {
        template.update( "INSERT INTO event (eventdate, eventtype) VALUES(?,?)", event.getEventDay(), event.getType().name());
    }
    
    private void update( Event event) {
        template.update( "UPDATE event SET eventdate = ?, eventtype = ? WHERE event_id = ?",
                            event.getEventDay(), event.getType().name());
    }
    
    private Event createEvent( ResultSet rs, int row) throws SQLException {
        var event = new Event();
        
        event.setId( rs.getLong( "event_id"));
        event.setEventDay( toLocalDate( rs.getDate( "eventdate")));
        event.setType( Event.Type.valueOf( rs.getString( "eventtype")));
        
        return event;
    }
    
    private LocalDate toLocalDate( Date date) {
        var instant = Instant.ofEpochMilli( date.getTime()); 
        
        return LocalDate.ofInstant( instant, ZoneId.systemDefault());
    }
}
