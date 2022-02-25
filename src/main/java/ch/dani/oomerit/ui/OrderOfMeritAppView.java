/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.service.OrderOfMeritService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;

/**
 *
 * @author dani
 */
@PWA(name = "Order of Merit", shortName = "OrderOfMerit")
@Viewport( "width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=no")
@PageTitle( "Order of Merit TTC Gelterkinden")
@Route( "")
public class OrderOfMeritAppView extends AppLayout implements AppShellConfigurator, RouterLayout {

    public OrderOfMeritAppView( SessionMgr session, OrderOfMeritService oomService) {
        Image logo = new Image( "/images/ttracket.png", "Logo");
        
        logo.setHeight( "30px");
        
        addToNavbar(new DrawerToggle(), logo, new H3( "Order of Merit TTCG"));

        var tabs = new Tabs();
        var rankingTab = new ActionTab( "Ranking", () -> UI.getCurrent().navigate( RankingView.class));
        
        tabs.add( rankingTab);
        
        if( session.isAdmin()) {
            var playerTab = new ActionTab( "Players", () -> UI.getCurrent().navigate( PlayerCRUD.class));
            var meritTab = new ActionTab ( "Merits", () -> UI.getCurrent().navigate( MeritCRUD.class));
            var eventTab = new ActionTab( "Events", () -> UI.getCurrent().navigate( EventCRUD.class));
            var userTab = new ActionTab( "Users", () -> UI.getCurrent().navigate( UserCRUD.class));

            tabs.add( playerTab, meritTab, eventTab, userTab);
        }
        
        if( session.isAdmin() || session.isTrainer()) {
            var addMeritsTab = new ActionTab( "Add Merits", () -> UI.getCurrent().navigate( EnterMeritsView.class));
    
            tabs.add( addMeritsTab);
        }
        
        if( !session.isLoggedIn()) {
            tabs.add( new ActionTab( "Login", () -> UI.getCurrent().navigate( LoginView.class)));
        }
        else {
            tabs.add( new ActionTab( "Logout", () -> UI.getCurrent().navigate( LogoutView.class)));
        }
        
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        
        tabs.addSelectedChangeListener( this::onTabChanged);
        
        addToDrawer(tabs);
        
        this.setContent( new RankingView( oomService));
    }

    private void onTabChanged(Tabs.SelectedChangeEvent event) {
        var tab = event.getSelectedTab();

        if( tab instanceof ActionTab at) {
            at.execute();
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
