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
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import java.util.function.Supplier;

/**
 *
 * @author dani
 */
@Route( "")
public class OrderOfMeritAppView extends AppLayout {

    public OrderOfMeritAppView( PlayerService playerService, EventService eventService, MeritService meritService, OrderOfMeritService oomService, UserService userService) {
        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
        img.setHeight("44px");
        addToNavbar(new DrawerToggle(), new H3( "Order of Merit TTC Gelterkinden"));
        
        var rankingTab = new ViewTab( "Ranking", () -> new RankingView( oomService));
        var playerTab = new ViewTab( "Players", () -> new PlayerCRUD( playerService));
        var meritTab = new ViewTab ( "Merits", () -> new MeritCRUD( meritService));
        var eventTab = new ViewTab( "Events", () -> new EventCRUD( eventService));
        var addMeritsTab = new ViewTab( "Add Merits", () -> new EnterMeritsView( playerService, eventService, meritService, oomService));
        var userTab = new ViewTab( "Users", () -> new UserCRUD( userService));
        
        var tabs = new Tabs( rankingTab, playerTab, meritTab, eventTab, addMeritsTab, userTab);

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        
        tabs.addSelectedChangeListener( this::onTabChanged);
        
        addToDrawer(tabs);
        
        this.setContent( new RankingView( oomService));
    }

    private void onTabChanged(Tabs.SelectedChangeEvent event) {
        this.setContent( ((ViewTab)event.getSelectedTab()).createView());
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
}
