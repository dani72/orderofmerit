/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.service;

import java.util.List;

/**
 *
 * @author dani
 */
public interface CrudService<T> {
    
    /**
     * Returns all entities
     * @return list of entities
     */
    List<T> findAll();
    
    /**
     * Returns all entities sorted by the sortFields
     * @param sortFields list of fields defining the sort order
     * @return list of entities sorted according to specification
     */
    List<T> findAll( List<SortField> sortFields);
    
    /**
     * Inserts or updates the entity
     * @param entity to save
     */
    void save( T entity);
    
    /**
     * Removes entity from backend
     * @param entity to remove
     */
    void remove( T entity);
}
