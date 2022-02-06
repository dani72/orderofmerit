/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

/**
 *
 * @author dani
 */
@Route( value = "logout", layout = OrderOfMeritAppView.class)
public class LogoutView extends Div implements BeforeEnterObserver {

    private final SessionMgr session;
    
    public LogoutView( SessionMgr session) {
        this.session = session;
    } 
    
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        this.session.logout();
        
        VaadinSession.getCurrent().close();
        
        this.add( new RouterLink( "Zum Ranking", RankingView.class));
    }
}
