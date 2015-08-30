package com.javiermoreno.bankof.bankoffunctionaltests;

import java.math.BigDecimal;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author ciberado
 */
public class TransaccionIT {
    
    private static WebDriver driver = null;
    
    public TransaccionIT() {
    }
    
    @BeforeClass
    public static void inicializarDriver() {
        driver = new ChromeDriver();
    }
    
    @AfterClass
    public static void liquidarDriver() {
        driver.quit();
    }
    

    @Test
    public void comprobarFlujoCorrectoTransferencia() {
        driver.get("http://localhost:9001/");
        
        WebElement origenElem = driver.findElement(By.id("origen"));
        origenElem.sendKeys("9999");  
        WebElement destinoElem = driver.findElement(By.id("destino"));
        destinoElem.sendKeys("9998");
        WebElement importeElem = driver.findElement(By.id("importe"));
        importeElem.sendKeys("1");
        
        WebElement cmdAceptar = driver.findElement(By.name("aceptar"));
        cmdAceptar.click();
        
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement saldoElem = driver.findElement(By.id("saldo"));
        wait.until(ExpectedConditions.visibilityOf(saldoElem));
        
        BigDecimal valorSaldo = new BigDecimal(saldoElem.getText());
        Assert.assertTrue("Saldo es positivo", 
                valorSaldo.compareTo(BigDecimal.ZERO) >= 0);
    }
    
}
