/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.service;

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
public class PlayerService extends AbstractCrudService<Player> {

    @Autowired
    private JdbcTemplate template;
    
    public Player findById(Long id) {
        return template.queryForObject( "SELECT * FROM player where player_id = ?", this::createPlayer, id);
    }

    @Override
    public List<Player> findAll() {
        return template.query( "SELECT * FROM player", this::createPlayer);
    }
    
    @Override
    public List<Player> findAll( List<SortField> sortFields) {
        return template.query( concatenateSql( "SELECT * FROM player", sortFields), this::createPlayer);
    }
    
    @Override
    protected String mapFieldName( SortField field) {
        return switch ( field.fieldname()) {
            case "dateOfBirth" -> "date_of_birth";
            default -> field.fieldname();
        };
    }
    
    @Override
    public void save( Player player) {
        if( player.getId() == null) {
            insert( player);
        }
        else {
            update( player);
        }
    }
    
    private void insert( Player player) {
        template.update( "INSERT INTO player (firstname, lastname, nickname, date_of_birth) VALUES (?,?,?,?)",
                            player.getFirstname(), player.getLastname(), player.getNickname(), player.getDateOfBirth());
    }
    
    private void update( Player player) {
        template.update( "UPDATE player SET firstname = ?, lastname = ?, nickname = ?, date_of_birth = ? WHERE player_id = ?",
                            player.getFirstname(), player.getLastname(), player.getNickname(), player.getDateOfBirth(), player.getId());
    }
    
    @Override
    public void remove( Player player) {
        template.update( "DELETE FROM player WHERE player_id = ?", player.getId());
    }
    
    private Player createPlayer( ResultSet rs, int rowNum) throws SQLException {
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
