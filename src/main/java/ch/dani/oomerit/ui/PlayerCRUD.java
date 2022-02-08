/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.Player;
import ch.dani.oomerit.service.PlayerService;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudGrid;
import com.vaadin.flow.component.crud.CrudVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

/**
 *
 * @author dani
 */
@Route( value = "players", layout = OrderOfMeritAppView.class)
public class PlayerCRUD extends AdminCRUD<Player> {
    
    private final CrudDataProvider provider;
    
    public PlayerCRUD( SessionMgr session, PlayerService service) {
        super( session, Player.class, new CrudGrid<Player>( Player.class, false), createPlayerEditor());
        
        this.provider = new CrudDataProvider<>( service);
        
        this.setDataProvider( this.provider);
        this.addSaveListener( this::persist);
        this.addDeleteListener( this::remove);
        
        this.getGrid().removeColumnByKey("id");
        this.getGrid().setColumns( "firstname", "nickname", "lastname", "dateOfBirth");
        Crud.addEditColumn( this.getGrid());
        
        this.addThemeVariants(CrudVariant.NO_BORDER);
        this.setHeightFull();
    }
    
    private void persist( SaveEvent<Player> e) {
        provider.persist( e.getItem());
    }
    
    private void remove( DeleteEvent<Player> e) {
        this.provider.delete( e.getItem());
    }
    
    private static CrudEditor<Player> createPlayerEditor() {
        TextField firstName = new TextField("Firstname");
        TextField lastName = new TextField("Lastname");
        TextField nickName = new TextField("Nickname");
        DatePicker dateOfBirth = new DatePicker( "Date of Birth");
        FormLayout form = new FormLayout(firstName, lastName, nickName, dateOfBirth);

        Binder<Player> binder = new Binder( Player.class);
        
        binder.bind( firstName, Player::getFirstname, Player::setFirstname);
        binder.bind( lastName, Player::getLastname, Player::setLastname);
        binder.bind( nickName, Player::getNickname, Player::setNickname);
        binder.bind( dateOfBirth, Player::getDateOfBirth, Player::setDateOfBirth);

        return new BinderCrudEditor<>(binder, form);
    }
}
