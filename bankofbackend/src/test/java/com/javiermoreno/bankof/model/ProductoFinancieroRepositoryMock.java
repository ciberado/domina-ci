/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javiermoreno.bankof.model;

import com.javiermoreno.bankof.model.domain.ProductoFinanciero;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ciberado
 */
public class ProductoFinancieroRepositoryMock implements ProductoFinancieroRepository {

    private Map<String /* codigo */, ProductoFinanciero> almacen = new HashMap<>();
    
    @Override
    public <S extends ProductoFinanciero> S save(S pf) {
        almacen.put(pf.getCodigo(), pf);
        return pf;
    }

    @Override
    public ProductoFinanciero findOne(String codigo) {
        return almacen.get(codigo);
    }

    @Override
    public <S extends ProductoFinanciero> Iterable<S> save(Iterable<S> itrbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exists(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<ProductoFinanciero> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<ProductoFinanciero> findAll(Iterable<String> itrbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(ProductoFinanciero t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Iterable<? extends ProductoFinanciero> itrbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
