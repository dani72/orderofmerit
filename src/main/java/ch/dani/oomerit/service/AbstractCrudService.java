/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit.service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author dani
 */
public abstract class AbstractCrudService<T> implements CrudService<T> {
    
    protected String concatenateSql( String base, List<SortField> sortFields) {
        var sql = new StringBuilder( base);
        
        if( !sortFields.isEmpty()) {
            sql.append( " ORDER BY ");
            sql.append( sortFields.stream().map( sf -> mapFieldName( sf) + " " + sf.direction().name()).collect( Collectors.joining( ",")));
        }
        
        return sql.toString();
    }
    
    protected String mapFieldName( SortField field) {
        return field.fieldname();
    }
}
