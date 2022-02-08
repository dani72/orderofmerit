/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.service;

import ch.dani.oomerit.domain.Merit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author dani
 */
@Service
public class MeritService implements CrudService<Merit> {
    
    @Autowired
    private JdbcTemplate template;
    
    public List<Merit> findByCategory( Merit.Category category) {
        return template.query( "SELECT * FROM merit where category = ? OR category = 'ALL'", MeritService::createMerit, category.name());
    }
    
    @Override
    public List<Merit> findAll() {
        return template.query( "SELECT * FROM merit", MeritService::createMerit);
    }
    
    public Merit findById(Long id) {
        return template.queryForObject( "SELECT * FROM merit where merit_id = ?", MeritService::createMerit, id);
    }
    
    @Override
    public void save( Merit merit) {
        if( merit.getId() == null) {
            insert( merit);
        }
        else {
            update( merit);
        }
    }
    
    @Override
    public void remove( Merit merit) {
        template.update( "DELETE FROM merit WHERE merit_id = ?", merit.getId());
    }
    
    private void insert( Merit merit) {
        template.update( "INSERT INTO merit (merit_name, category, points) VALUES (?,?,?)", 
                            merit.getName(), merit.getCategory().name(), merit.getPoints());
    }
    
    private void update( Merit merit) {
        template.update( "UPDATE MERIT SET merit_name = ? , category = ?, points = ? WHERE merit_id = ?",
                            merit.getName(), merit.getCategory().name(), merit.getPoints(), merit.getId());
    }
    
    public static Merit createMerit( ResultSet rs, int rowNum) throws SQLException {
        var merit = new Merit();
        
        merit.setId( rs.getLong( "merit_id"));
        merit.setName( rs.getString( "merit_name"));
        merit.setCategory( Merit.Category.valueOf( rs.getString( "category")));
        merit.setPoints( rs.getInt( "points"));
        return merit;
    }
}
