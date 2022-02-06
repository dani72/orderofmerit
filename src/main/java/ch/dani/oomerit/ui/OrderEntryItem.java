/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.OrderEntry;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;

/**
 *
 * @author dani
 */
@Tag("oomerit-entry")
@JsModule("./oomerit-entry.js")
public class OrderEntryItem extends LitTemplate {
    
    public OrderEntryItem( OrderEntry entry) {
        this.getElement().setProperty( "nickname", entry.getPlayer().getNickname());
        this.getElement().setProperty( "firstname", entry.getPlayer().getFirstname());
        this.getElement().setProperty( "lastname", entry.getPlayer().getLastname());
        this.getElement().setProperty( "points", Integer.toString( entry.getNofMerits()));
    }
}
