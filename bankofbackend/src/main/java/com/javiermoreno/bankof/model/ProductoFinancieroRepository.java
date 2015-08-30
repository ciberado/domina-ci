/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javiermoreno.bankof.model;

import com.javiermoreno.bankof.model.domain.ProductoFinanciero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ciberado
 */
@Repository
public interface ProductoFinancieroRepository extends CrudRepository<ProductoFinanciero, String> {
    
}
