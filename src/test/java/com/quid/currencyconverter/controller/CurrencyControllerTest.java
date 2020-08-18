package com.quid.currencyconverter.controller;

import com.quid.currencyconverter.CurrencyConverter;
import com.quid.currencyconverter.dto.ExchangeResultDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class) // difference?
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CurrencyConverter.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

//    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private String convertUrl;
    private JSONObject convertJsonRequest;

    @Before
    public void setUp() throws JSONException {
//        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        convertUrl = "http://localhost:" + port + "/convert";

        convertJsonRequest = new JSONObject();
        convertJsonRequest.put("fromCurrencyValue", "1000");
//        convertJsonRequest.put("exchangeResultValue", "1000");
        convertJsonRequest.put("fromCurrency", "USD");
        convertJsonRequest.put("toCurrency", "BYN");
    }

    @Test
    public void convert() {
        HttpEntity<String> request = new HttpEntity<>(convertJsonRequest.toString(), headers);
//        ExchangeResultDTO resultDTO = restTemplate.postForObject(convertUrl, request, ExchangeResultDTO.class);
        ResponseEntity<ExchangeResultDTO> resultDTOResponseEntity = restTemplate.postForEntity(convertUrl, request, ExchangeResultDTO.class);
//        System.out.println(resultDTO);
        System.out.println(resultDTOResponseEntity.getBody());
    }
}