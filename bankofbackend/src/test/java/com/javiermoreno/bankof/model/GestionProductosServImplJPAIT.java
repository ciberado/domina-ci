package com.javiermoreno.bankof.model;

import com.javiermoreno.bankof.BankOfBackendApplication;
import com.javiermoreno.bankof.model.domain.CuentaCorriente;
import java.math.BigDecimal;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ciberado
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BankOfBackendApplication.class)
@WebIntegrationTest(randomPort = true)
public class GestionProductosServImplJPAIT {

    public static final String CODIGO_A = "1111";
    public static final String CODIGO_B = "2222";
    public static final String CODIGO_INEXISTENTE = "333";
    
    @Value("${local.server.port}")
    private int port;

    @Autowired
    ProductoFinancieroRepository pfRepo;

    public String getTransferenciasURL() {
        return "http://127.0.0.1:" + port + "/transferencias";
    }

    @Before
    public void inicializar() {
        // Reiniciará el estado de la base de datos cada vez que se vaya a ejecutar un test.
        pfRepo.save(new CuentaCorriente(CODIGO_A, new BigDecimal("1000"), new BigDecimal("0.02")));
        pfRepo.save(new CuentaCorriente(CODIGO_B, new BigDecimal("0"), new BigDecimal("0.02")));
        if (pfRepo.findOne(CODIGO_INEXISTENTE) != null) throw new RuntimeException("Base de datos en estado incorrecto.");
    }

    @Test
    public void transferenciaCorrecta() throws JSONException {
        JSONObject request = new JSONObject();
        request.put("origen", CODIGO_A);
        request.put("destino", CODIGO_B);
        request.put("importe", BigDecimal.TEN);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .exchange(getTransferenciasURL(), HttpMethod.POST, entity, String.class);
        Assert.assertEquals("Se ha realizado la transferencia.",  HttpStatus.CREATED, response.getStatusCode());
        JSONObject responseBody = new JSONObject(response.getBody());
        String codigoOrigen = responseBody.getJSONObject("origen").getString("codigo");
        Assert.assertEquals("El origen de la transferencia corresponde con el deseado", "1111", codigoOrigen);
    }

    @Test(expected = org.springframework.web.client.HttpClientErrorException.class)
    public void transferenciaDestinoNoEncontrado() throws JSONException {
        JSONObject request = new JSONObject();
        request.put("origen", CODIGO_A);
        request.put("destino", CODIGO_INEXISTENTE);
        request.put("importe", BigDecimal.TEN);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .exchange(getTransferenciasURL(), HttpMethod.POST, entity, String.class);
    }
    
    
}
