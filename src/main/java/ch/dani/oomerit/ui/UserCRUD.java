/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.User;
import ch.dani.oomerit.service.UserService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudGrid;
import com.vaadin.flow.component.crud.CrudVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

/**
 *
 * @author dani
 */
@Route( value = "users", layout = OrderOfMeritAppView.class)
public class UserCRUD extends AdminCRUD<User> {
    
    private final CrudDataProvider<User> provider;
    
    public UserCRUD( SessionMgr session, UserService service) {
        super( session, User.class, new CrudGrid<User>( User.class, false), createUserEditor());
        
        this.provider = new CrudDataProvider<>( service);
        
        this.setDataProvider( this.provider);
        this.addSaveListener( this::persist);
        this.addDeleteListener( this::remove);
        
        this.getGrid().removeColumnByKey("id");
        this.getGrid().setColumns( "username", "mail", "role");
        Crud.addEditColumn( this.getGrid());
        
        this.addThemeVariants(CrudVariant.NO_BORDER);
        this.setHeightFull();
    }
    
    private void persist( SaveEvent<User> e) {
        provider.persist( e.getItem());
    }
    
    private void remove( DeleteEvent<User> e) {
        this.provider.delete( e.getItem());
    }
    
    private static CrudEditor<User> createUserEditor() {
        var username = new TextField("User");
        var mail = new EmailField("e-Mail");
        var role = new ComboBox<User.Role>( "Role", User.Role.values());

        var form = new FormLayout( username, mail, role);
        var binder = new Binder<User>( User.class);
        
        binder.bind( username, User::getUsername, User::setUsername);
        binder.bind( mail, User::getMail, User::setMail);
        binder.bind( role, User::getRole, User::setRole);

        return new BinderCrudEditor<>(binder, form);
    }
}
