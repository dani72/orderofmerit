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
import com.vaadin.flow.component.crud.CrudGrid;
import com.vaadin.flow.component.crud.CrudVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;

/**
 *
 * @author dani
 */
@Route( value = "merits", layout = OrderOfMeritAppView.class)
public class MeritCRUD extends AdminCRUD<Merit> {
    
    private final SessionMgr session;
    private final CrudDataProvider<Merit> provider;
    
    public MeritCRUD( SessionMgr session, MeritService service) {
        super( session, Merit.class, new CrudGrid<Merit>( Merit.class, false), createMeritEditor());
        
        this.session = session;
        this.provider = new CrudDataProvider<>( service);
        
        this.setDataProvider( this.provider);
        this.addSaveListener( this::persist);
        this.addDeleteListener( this::remove);
        
        this.getGrid().removeColumnByKey("id");
        this.getGrid().setColumns( "name", "category", "points");
        this.getGrid().getColumnByKey( "name").setWidth( "50%");
        this.getGrid().getColumnByKey( "category").setWidth( "20%");
        this.getGrid().getColumnByKey( "points").setTextAlign(ColumnTextAlign.END).setWidth( "20%");
        Crud.addEditColumn( this.getGrid());
        
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
        var points = new IntegerField( "Points");
        var form = new FormLayout( name, category, points);

        category.setItems( Merit.Category.values());
        
        var binder = new Binder<Merit>( Merit.class);
        
        binder.bind( name, Merit::getName, Merit::setName);
        binder.bind( category, Merit::getCategory, Merit::setCategory);
        binder.bind( points, Merit::getPoints, Merit::setPoints);

        return new BinderCrudEditor<>(binder, form);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if( !session.isAdmin()) {
            event.rerouteTo( RankingView.class);
        }
    }
}
