/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.User;
import ch.dani.oomerit.service.UserService;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import java.util.stream.Stream;

/**
 *
 * @author dani
 */
public class UserDataProvider extends AbstractBackEndDataProvider<User, CrudFilter> {

    private final UserService service;

    public UserDataProvider( UserService service) {
        this.service = service;
    }
    
    @Override
    protected Stream<User> fetchFromBackEnd(Query<User, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        return service.findAll().stream().skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<User, CrudFilter> query) {
        return service.findAll().size();
    }

    public void persist(User user) {
        service.save( user);
    }

    public void delete(User user) {
        service.remove( user);
    }
}
