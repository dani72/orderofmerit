/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.Merit;
import ch.dani.oomerit.service.MeritService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

/**
 *
 * @author dani
 */
public class MeritCRUD extends Crud<Merit> {
    
    private final MeritDataProvider provider;
    
    public MeritCRUD( MeritService service) {
        super( Merit.class, createMeritEditor());
        
        this.provider = new MeritDataProvider( service);
        
        this.setDataProvider( this.provider);
        this.addSaveListener( this::persist);
        this.addDeleteListener( this::remove);
        
        this.getGrid().removeColumnByKey("id");
//        this.getGrid().setColumns( "firstname", "lastname", "nickname", "dateOfBirth");
        
        this.addThemeVariants(CrudVariant.NO_BORDER);
        this.setHeightFull();
    }
    
    private void persist( SaveEvent<Merit> e) {
        provider.persist( e.getItem());
    }
    
    private void remove( DeleteEvent<Merit> e) {
        this.provider.delete( e.getItem());
    }
    
    private static CrudEditor<Merit> createMeritEditor() {
        var name = new TextField("Name");
        var category = new ComboBox<Merit.Category>( "Category");
        var form = new FormLayout( name, category);

        category.setItems( Merit.Category.values());
        
        var binder = new Binder<Merit>( Merit.class);
        
        binder.bind( name, Merit::getName, Merit::setName);
        binder.bind( category, Merit::getCategory, Merit::setCategory);

        return new BinderCrudEditor<>(binder, form);
    }
}
