/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.service.CrudService;
import ch.dani.oomerit.service.SortField;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import java.util.List;
import java.util.stream.Collectors;
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

        return service.findAll( mapSortOrder( query.getSortOrders())).stream().skip(offset).limit(limit);
    }
    
    private List<SortField> mapSortOrder( List<QuerySortOrder> sortOrders) {
        return sortOrders.stream().map( this::createSortField).collect( Collectors.toList());
    }
    
    private SortField createSortField( QuerySortOrder sortOrder) {
        return new SortField( sortOrder.getDirection() == SortDirection.ASCENDING ? SortField.Direction.ASC : SortField.Direction.DESC, sortOrder.getSorted());
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
