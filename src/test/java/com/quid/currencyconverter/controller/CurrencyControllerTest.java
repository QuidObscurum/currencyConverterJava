package com.quid.currencyconverter.controller;

import com.quid.currencyconverter.CurrencyConverter;
import com.quid.currencyconverter.dto.ExchangeResultDTO;
import com.quid.currencyconverter.myutils.CurrencyCode;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class) // difference?
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CurrencyConverter.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    private String convertUrl;
    private JSONObject convertJsonRequest;

    @Before
    public void setUp() throws JSONException {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        convertUrl = "http://localhost:" + port + "/convert";

        convertJsonRequest = new JSONObject();
        convertJsonRequest.put("fromCurrencyValue", "1000");
//        convertJsonRequest.put("exchangeResultValue", "1000");
        convertJsonRequest.put("fromCurrency", CurrencyCode.USD.toString());
        convertJsonRequest.put("toCurrency", CurrencyCode.BYN.toString());
    }

    @Test
    public void shouldConvert() {
        HttpEntity<String> request = new HttpEntity<>(convertJsonRequest.toString(), headers);
//        ExchangeResultDTO resultDTO = restTemplate.postForObject(convertUrl, request, ExchangeResultDTO.class);
        ResponseEntity<ExchangeResultDTO> resultDTOResponseEntity = restTemplate.postForEntity(convertUrl, request, ExchangeResultDTO.class);
//        System.out.println(resultDTO);
        System.out.println(resultDTOResponseEntity.getBody());
        assertThat(resultDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultDTOResponseEntity.getBody()).isNotNull();
        assertThat(resultDTOResponseEntity.getBody().getExchangeResultValue())
                .isNotEqualTo(resultDTOResponseEntity.getBody().getFromCurrencyValue());

    }

    @Test
    public void shouldReturnResponseWhenFieldError() {
//        when "fromCurrencyValue" : -1000
//        {
//            "path": "/convert",
//            "status": 400,
//            "error": "Bad Request",
//            "message": "Positive (fromCurrencyValue): must be greater than 0."
//        }
        try {
            convertJsonRequest.put("fromCurrencyValue", "-1000");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(convertJsonRequest);
        HttpEntity<String> request = new HttpEntity<>(convertJsonRequest.toString(), headers);
        ResponseEntity<CurrencyController.MyErrorResponse> errorResponseEntity = restTemplate
                .postForEntity(convertUrl, request, CurrencyController.MyErrorResponse.class);

        System.out.println(errorResponseEntity.getBody());
        assertErrorResponse(errorResponseEntity, "Positive (fromCurrencyValue): must be greater than 0.");

//        when no "fromCurrency": "BYN"
//        {
//            "path": "/convert",
//            "status": 400,
//            "error": "Bad Request",
//            "message": "NotNull (fromCurrency): Currency code cannot be null or empty."
//        }

        try {
            convertJsonRequest.remove("fromCurrency");
            convertJsonRequest.put("fromCurrencyValue", "1000");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(convertJsonRequest);
        request = new HttpEntity<>(convertJsonRequest.toString(), headers);
        errorResponseEntity = restTemplate
                .postForEntity(convertUrl, request, CurrencyController.MyErrorResponse.class);
        System.out.println(errorResponseEntity.getBody());
        assertErrorResponse(errorResponseEntity, "NotNull (fromCurrency): Currency code cannot be null or empty.");

//        when "fromCurrencyValue" : -1000, no "fromCurrency": "BYN",
//        {
//            "path": "/convert",
//            "status": 400,
//            "error": "Bad Request",
//            "message": "NotNull (fromCurrency): Currency code cannot be null or empty; Positive (fromCurrencyValue): must be greater than 0."
//        }

//        when "fromCurrencyValue" : -1000, "fromCurrency": "",
//        {
//            "timestamp": "2020-08-19T14:16:42.263+00:00",
//                "status": 400,
//                "error": "Bad Request",
//                "message": "",
//                "path": "/convert"
//        }
    }

    private void assertErrorResponse(
            ResponseEntity<CurrencyController.MyErrorResponse> errorResponseEntity,
            String customMsg
    ) {
        assertThat(errorResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorResponseEntity.getBody()).isNotNull();
        assertThat(errorResponseEntity.getBody().status)
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponseEntity.getBody().message)
                .isEqualTo(customMsg);
        assertThat(errorResponseEntity.getBody().error)
                .isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }
}