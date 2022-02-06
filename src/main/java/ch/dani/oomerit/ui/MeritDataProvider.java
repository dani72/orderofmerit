/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.Merit;
import ch.dani.oomerit.service.MeritService;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import java.util.stream.Stream;

/**
 *
 * @author dani
 */
public class MeritDataProvider extends AbstractBackEndDataProvider<Merit, CrudFilter> {

    private final MeritService service;

    public MeritDataProvider(  MeritService service) {
        this.service = service;
    }
    
    @Override
    protected Stream<Merit> fetchFromBackEnd(Query<Merit, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        return service.findAll().stream().skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<Merit, CrudFilter> query) {
        return service.findAll().size();
    }

    public void persist(Merit player) {
        service.save( player);
    }

    public void delete(Merit player) {
        service.remove( player);
    }
}
