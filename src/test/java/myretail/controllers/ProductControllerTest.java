package myretail.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import myretail.exception.ProductNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

    RestTemplate restTemplate = new RestTemplate();

    @Test (expected = Exception.class)
    public void getUnknownProductInfo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        Map<String, String> map = new HashMap<String, String>();

        String url = "http://localhost:8080/products/138604";

        ResponseEntity response = new ResponseEntity(null, headers , HttpStatus.NOT_FOUND);
        when(restTemplate.getForEntity(url, response.getClass(), map)).thenReturn(response);

        assertEquals(404, response.getStatusCode().value());

        return;
    }

    @Test (expected = Exception.class)
    public void PutKnownProductInfo() {

        HttpHeaders requestHeaders = new HttpHeaders();
        List<MediaType> mediaTypeList = new ArrayList<MediaType>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(mediaTypeList);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);


        String requestBody = "{\"id\":142555346,\"name\":\"The Big Lebowski (Blu-ray) (Widescreen)\",\"current_price\":{\"value\": 13.49,\"currency_code\":\"USD\"}}";
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestBody, requestHeaders);

        String url = "http://localhost:8080/products/{id}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "138604289");

        ResponseEntity responseEntity = new ResponseEntity(null, requestHeaders , HttpStatus.NOT_FOUND);

        // Create the HTTP PUT request,
        when(restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseEntity.getClass(), map)).thenReturn(responseEntity);
    }

}