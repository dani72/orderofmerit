/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.service.CrudService;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import java.util.stream.Stream;

/**
 *
 * @author dani
 */
public class CrudDataProvider<T> extends AbstractBackEndDataProvider<T, CrudFilter> {

    private final CrudService<T> service;

    public CrudDataProvider( CrudService<T> service) {
        this.service = service;
    }
    
    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        return service.findAll().stream().skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<T, CrudFilter> query) {
        return service.findAll().size();
    }

    public void persist( T entity) {
        service.save( entity);
    }

    public void delete( T entity) {
        service.remove( entity);
    }
}
