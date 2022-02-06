/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.ui;

import ch.dani.oomerit.domain.Player;
import ch.dani.oomerit.service.PlayerService;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import java.util.stream.Stream;

/**
 *
 * @author dani
 */
public class PlayerDataProvider extends AbstractBackEndDataProvider<Player, CrudFilter> {

    private final PlayerService service;

    public PlayerDataProvider( PlayerService service) {
        this.service = service;
    }
    
    @Override
    protected Stream<Player> fetchFromBackEnd(Query<Player, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        return service.findAll().stream().skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<Player, CrudFilter> query) {
        return service.findAll().size();
    }

    public void persist(Player player) {
        service.save( player);
    }

    public void delete(Player player) {
        service.remove( player);
    }
}
