/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.domain;

/**
 *
 * @author dani
 */
public class OrderEntry {
    
    private Player player;
    private int totalPoints;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int points) {
        this.totalPoints = points;
    }
}
