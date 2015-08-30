package com.javiermoreno.bankof.model.domain;

import com.javiermoreno.dominaspring.framework.BusinessException;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author ciberado
 */
public class CuentaCorrienteTest {
    
    private CuentaCorriente a;
    private CuentaCorriente b;
    
    @Before
    public void inicializar() {
        a = new CuentaCorriente("1111", new BigDecimal("1000"), new BigDecimal("0.02"));
        b = new CuentaCorriente("2222", new BigDecimal("0"), new BigDecimal("0.02"));
    }

    @Test(expected = BusinessException.class)
    public void transferirNegativo() {
        a.transferir(b, new BigDecimal("-1"));
    }
    
    @Test(expected = BusinessException.class)
    public void transferirSinSaldoSuficiente() {
        b.transferir(a, new BigDecimal("1"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void transferirALaPropiaCuenta() {
        a.transferir(a, new BigDecimal("1"));
    }
    
    @Test
    public void transferenciaCorrecta() {
        a.transferir(b, new BigDecimal("1"));
        Assert.assertEquals("La cuenta de origen tiene un euro menos.", new BigDecimal("999"), a.getSaldo());
        Assert.assertEquals("La cuenta de desitno tiene un euro más.", new BigDecimal("1"), b.getSaldo());
    }
    
    
    
}
