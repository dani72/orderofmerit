/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.service;

import ch.dani.oomerit.domain.User;
import ch.dani.oomerit.security.PasswordChecker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.security.auth.login.FailedLoginException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author dani
 */
@Service
public class UserService {

    @Autowired
    private JdbcTemplate template;
    
    public User authenticate( String username, String password) throws FailedLoginException {
        try {
            User user = template.queryForObject( "SELECT * FROM users where username = ?", this::createUser, username);
            String pwdhash = template.queryForObject( "SELECT pwdhash FROM users where username = ?", String.class, username);

            if( (user != null) || !StringUtils.isBlank( pwdhash)) {
                if( PasswordChecker.instance().verifyPassword(password, pwdhash)) {
                    return user;
                }
            }
        }
        catch( Exception e) {
            // Intentionally empty
        }
        
        throw new FailedLoginException( "Could not authenticate user.");
    }
    
    public User findById(Long id) {
        return template.queryForObject( "SELECT * FROM users where user_id = ?", this::createUser, id);
    }

    public List<User> findAll() {
        return template.query( "SELECT * FROM users", this::createUser);
    }
    
    public void save( User user) {
        if( user.getId() == null) {
            insert( user);
        }
        else {
            update( user);
        }
    }
    
    private void insert( User user) {
        template.update( "INSERT INTO users (username, mail, rolename) VALUES (?,?,?)",
                            user.getUsername(), user.getMail(), user.getRole().name());
    }
    
    private void update( User user) {
        template.update( "UPDATE users SET username = ?, mail = ?, rolename = ? WHERE user_id = ?",
                            user.getUsername(), user.getMail(), user.getRole().name(), user.getId());
    }
    
    public void remove( User user) {
        template.update( "DELETE FROM users WHERE player_id = ?", user.getId());
    }
    
    private User createUser( ResultSet rs, int rowNum) throws SQLException {
        var user = new User();
        
        user.setId( rs.getLong( "user_id"));
        user.setUsername( rs.getString( "username"));
        user.setMail( rs.getString( "mail"));
        user.setRole( User.Role.valueOf( rs.getString( "rolename")));
        
        return user;
    }
}
