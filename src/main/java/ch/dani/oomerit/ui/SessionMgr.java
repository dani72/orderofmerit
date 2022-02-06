/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.User;
import ch.dani.oomerit.service.UserService;
import javax.security.auth.login.FailedLoginException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author dani
 */
@Component
@Scope( WebApplicationContext.SCOPE_SESSION)
public class SessionMgr {
    
    private final UserService userService;
    private User user;
    
    public SessionMgr( UserService userService) {
        this.userService = userService;
    }

    public void login( String username, String password) throws FailedLoginException {
        this.user = userService.authenticate( username, password);
    }
    
    public boolean isAdmin() {
        return (user != null) && user.getRole() == User.Role.ADMIN;
    }
    
    public boolean isTrainer() {
        return (user != null) && user.getRole() == User.Role.TRAINER;
    }
    
    public boolean isLoggedIn() {
        return user != null;
    }
}
