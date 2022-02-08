/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.service;

/**
 *
 * @author dani
 */
public record SortField( Direction direction, String fieldname) {
    public enum Direction { ASC, DESC };
}
