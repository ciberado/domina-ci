package com.javiermoreno.bankof.model;

import com.javiermoreno.bankof.model.domain.CuentaCorriente;
import com.javiermoreno.bankof.model.domain.Movimiento;
import com.javiermoreno.dominaspring.framework.BusinessException;
import java.math.BigDecimal;
import javax.persistence.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author ciberado
 */
public class GestionProductosServImplJPATest {
    
    ProductoFinancieroRepositoryMock pfRepo;
    MovimientoRepositoryMock mRepo;
    GestionProductosServImplJPA servicio;
    

    @Before
    public void inicializar() {
        pfRepo = new ProductoFinancieroRepositoryMock();
        pfRepo.save(new CuentaCorriente("1111", new BigDecimal("1000"), new BigDecimal("0.02")));
        pfRepo.save(new CuentaCorriente("2222", new BigDecimal("0"), new BigDecimal("0.02")));
        mRepo = new MovimientoRepositoryMock();
        servicio = new GestionProductosServImplJPA(pfRepo, mRepo);
    }
    
    @Test(expected = EntityNotFoundException.class)
    public void transferirACuentaInexistenteFalla() {
        servicio.transferir("1111", "9999", new BigDecimal("1"));
    }
    
    @Test(expected = BusinessException.class)
    public void transferirPorEncimaDeLimiteFalla() {
        servicio.transferir("1111", "2222", GestionProductosServImplJPA.MAXIMO_PERMITIDO.add(BigDecimal.ONE));
    }
    
    @Test(expected = BusinessException.class)
    public void transferirIncluyendoComisionSinSaldoSuficienteFalla() {
        BigDecimal maximoAceptable = new BigDecimal("1000").subtract(GestionProductosServImplJPA.IMPORTE_COMISION);
        servicio.transferir("1111", "2222", maximoAceptable.add(BigDecimal.ONE));
    }
    
    @Test
    public void transferenciaCorrecta() {
        Movimiento mov = servicio.transferir("1111", "2222", BigDecimal.TEN);
        BigDecimal importeMasComision = BigDecimal.TEN.add(GestionProductosServImplJPA.IMPORTE_COMISION);
        
        BigDecimal saldoOrigen = pfRepo.findOne("1111").getSaldo();
        Assert.assertTrue("El saldo de origen se ha reducido en importe + comisión.", 
                new BigDecimal("1000").subtract(importeMasComision).compareTo(saldoOrigen) == 0);
        
        BigDecimal saldoDestino = pfRepo.findOne("2222").getSaldo();
        Assert.assertTrue("El saldo de destino se ha incrementado en importe.", 
                new BigDecimal("0").add(BigDecimal.TEN).compareTo(saldoDestino) == 0);
        
        Assert.assertEquals("Deberían existir dos movimientos por transferencia", 2, mRepo.count());
    }
    
}
