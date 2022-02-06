/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.service.EventService;
import ch.dani.oomerit.service.MeritService;
import ch.dani.oomerit.service.OrderOfMeritService;
import ch.dani.oomerit.service.PlayerService;
import ch.dani.oomerit.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import java.util.function.Supplier;

/**
 *
 * @author dani
 */
@PWA(name = "Order of Merit", shortName = "OrderOfMerit")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=no")
@Route( "")
public class OrderOfMeritAppView extends AppLayout implements AppShellConfigurator {

    public OrderOfMeritAppView( SessionMgr session, PlayerService playerService, EventService eventService, MeritService meritService, OrderOfMeritService oomService, UserService userService) {
//        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
//        img.setHeight("44px");
        addToNavbar(new DrawerToggle(), new H3( "Order of Merit TTC Gelterkinden"));

        var tabs = new Tabs();
        var rankingTab = new ViewTab( "Ranking", () -> new RankingView( oomService));
        
        tabs.add( rankingTab);
        
        if( session.isAdmin()) {
            var playerTab = new ViewTab( "Players", () -> new PlayerCRUD( playerService));
            var meritTab = new ViewTab ( "Merits", () -> new MeritCRUD( meritService));
            var eventTab = new ViewTab( "Events", () -> new EventCRUD( eventService));
            var userTab = new ViewTab( "Users", () -> new UserCRUD( userService));

            tabs.add( playerTab, meritTab, eventTab, userTab);
        }
        
        if( session.isAdmin() || session.isTrainer()) {
            var addMeritsTab = new ViewTab( "Add Merits", () -> new EnterMeritsView( playerService, eventService, meritService, oomService));
    
            tabs.add( addMeritsTab);
        }
        
        if( !session.isLoggedIn()) {
            tabs.add( new ActionTab( "Login", () -> UI.getCurrent().navigate( LoginView.class)));
        }
        
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        
        tabs.addSelectedChangeListener( this::onTabChanged);
        
        addToDrawer(tabs);
        
        this.setContent( new RankingView( oomService));
    }

    private void onTabChanged(Tabs.SelectedChangeEvent event) {
        var tab = event.getSelectedTab();

        if( tab instanceof ViewTab vt) {
            this.setContent( vt.createView());
        }
        else if( tab instanceof ActionTab at) {
            at.execute();
        }
        
        if( this.isOverlay()) {
            this.setDrawerOpened( false);
        }
    }
    
    private class ViewTab extends Tab {
        
        private final Supplier<Component> factory;
        
        public ViewTab( String title, Supplier<Component> factory) {
            super( title);
            
            this.factory = factory;
        }
        
        public Component createView() {
            return factory.get();
        }
    }
    
    private class ActionTab extends Tab {
        
        private final Runnable action;
        
        public ActionTab( String title, Runnable action) {
            super( title);
            
            this.action = action;
        }
        
        public void execute() {
            this.action.run();
        }
    }
}
