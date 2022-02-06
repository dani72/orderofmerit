/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import javax.security.auth.login.FailedLoginException;

/**
 *
 * @author dani
 */
@Route( "login")
public class LoginView extends VerticalLayout {
    
    private final SessionMgr session;
    
    private LoginForm login;
    
    public LoginView( SessionMgr sessionMgr) {
        this.session = sessionMgr;
        
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
                
        this.login = new LoginForm();
        
        this.add( new H1( "Order of Merit"), login);
        
        login.addLoginListener( this::onLogin);
    }
    
    private void onLogin( LoginEvent event) {
        try {
            session.login( event.getUsername(), event.getPassword());
            
            UI.getCurrent().navigate( OrderOfMeritAppView.class);
        }
        catch( FailedLoginException e) {
            this.login.setError( true);
        }
    }
}
