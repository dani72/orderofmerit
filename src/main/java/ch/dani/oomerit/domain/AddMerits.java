/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dani
 */
public class AddMerits {
    public Player player;
    public Event event;
    public final Set<Merit> merits = new HashSet<>();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<Merit> getMerits() {
        return Collections.unmodifiableSet( this.merits);
    }
    
    public void setMerits( Set<Merit> merits) {
        this.merits.clear();
        this.merits.addAll( merits);
    }
}
