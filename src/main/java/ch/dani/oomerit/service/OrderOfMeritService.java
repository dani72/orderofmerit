/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.service;

import ch.dani.oomerit.domain.Event;
import ch.dani.oomerit.domain.MeritsReceived;
import ch.dani.oomerit.domain.Merit;
import ch.dani.oomerit.domain.OrderEntry;
import ch.dani.oomerit.domain.Player;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author dani
 */
@Service
public class OrderOfMeritService {

        private static final String sql = """
select p.*, CASE WHEN total_points IS NULL THEN 0 ELSE total_points END from 
player p left join
(select mir.player_id, sum(m.points) total_points from merit_received mir join merit m on m.merit_id = mir.merit_id group by mir.player_id) m
on p.player_id = m.player_id
order by total_points desc
""";

    @Autowired
    private JdbcTemplate template;
    
    public List<OrderEntry> getOrderOfMerit() {
        return template.query( sql, this::createOrderEntry);
    }
    
    public MeritsReceived getMeritsReceived( Event event, Player player) {
        var merits = template.query( "SELECT * FROM merit where merit_id IN (SELECT merit_id FROM merit_received WHERE event_id = ? AND player_id = ?)", MeritService::createMerit, event.getId(), player.getId());
        var mr = new MeritsReceived();
        
        mr.setEvent( event);
        mr.setPlayer( player);
        mr.setMerits( merits);
        
        return mr;
    }
    
    public void updateMeritsReceived( MeritsReceived addmerits) {
        template.update( "DELETE FROM merit_received WHERE player_id = ? AND event_id = ?", addmerits.getPlayer().getId(), addmerits.getEvent().getId());
        
        for( Merit m : addmerits.getMerits()) {
            template.update( "INSERT INTO merit_received (player_id, event_id, merit_id) VALUES( ?,?,?)",
                                addmerits.getPlayer().getId(), addmerits.getEvent().getId(), m.getId());
        }
    }
    
    private OrderEntry createOrderEntry( ResultSet rs, int row) throws SQLException {
        var entry = new OrderEntry();
        
        entry.setPlayer( createPlayer( rs));
        entry.setTotalPoints( rs.getInt( "total_points"));
        
        return entry;
    }
    
    private Player createPlayer( ResultSet rs) throws SQLException {
        var player = new Player();
        
        player.setId( rs.getLong( "player_id"));
        player.setFirstname( rs.getString( "firstname"));
        player.setLastname( rs.getString( "lastname"));
        player.setNickname( rs.getString( "nickname"));
        player.setDateOfBirth( toLocalDate( rs.getDate( "date_of_birth")));
        
        return player;
    }
    
    private LocalDate toLocalDate( Date date) {
        var instant = Instant.ofEpochMilli( date.getTime()); 
        
        return LocalDate.ofInstant( instant, ZoneId.systemDefault());
    }
}
