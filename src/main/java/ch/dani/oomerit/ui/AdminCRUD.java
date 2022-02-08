/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

/**
 *
 * @author dani
 */
public abstract class AdminCRUD<T> extends Crud<T> implements BeforeEnterObserver {

    private final SessionMgr session;
    
    protected AdminCRUD( SessionMgr session, Class<T> beanType, CrudEditor<T> editor) {
        super( beanType, editor);

        this.session = session;
    }
    
    protected AdminCRUD( SessionMgr session, Class<T> beanType, Grid<T> grid, CrudEditor<T> editor) {
        super( beanType, grid, editor);
        
        this.session = session;
    }
    
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if( !session.isAdmin()) {
            event.rerouteTo( RankingView.class);
        }
    }
}
