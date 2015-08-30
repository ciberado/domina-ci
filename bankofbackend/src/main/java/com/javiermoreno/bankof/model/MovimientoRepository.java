/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javiermoreno.bankof.model;

import com.javiermoreno.bankof.model.domain.Movimiento;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ciberado
 */
public interface MovimientoRepository extends CrudRepository<Movimiento, Integer> {
    
}
