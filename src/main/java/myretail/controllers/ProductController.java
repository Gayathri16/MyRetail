package myretail.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import myretail.data.Product;
import myretail.exception.ProductMisMatchException;
import myretail.repository.ProductRepository;
import myretail.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Api (value="MyRetailAPI", description = "Retrieve/Modify Product Information by product id")
@RestController
public class ProductController {

    @Autowired
    private ProductRepository repository;

    /**
     * Gets productId information from Target Redsky service and pricing information from MongoDB NoSQL
     * database and gives out a JSON response.
     * @param productId Id of product we need information about.
     * @return
     * @throws ProductNotFoundException
     */
    @ApiOperation(value = "Gets the product and price information by product id")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Success response"),
                    @ApiResponse(code = 404, message = "Product not found")})
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Product getProductInfo(@PathVariable ("id") String productId) throws ProductNotFoundException {

        RestTemplate restTemplate = new RestTemplate();
        Product product = new Product();

        String url = "http://redsky.target.com/v1/pdp/tcin/"+ productId +"?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
        Map<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("id", productId);

        ObjectMapper infoMapper = new ObjectMapper();
        Map<String, Map> infoMap;

        try {

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, urlVariables);
            infoMap = infoMapper.readValue(response.getBody(), Map.class);

            product.productId = productId;
            Map<String,Map> productMap = infoMap.get("product");
            Map<String,Map> itemMap = productMap.get("item");
            Map<String,String> prodDescrMap = itemMap.get(("product_description"));
            product.title = prodDescrMap.get("title");

            Product productInfoFromRepo = repository.findByProductId(productId);

            product.current_price = productInfoFromRepo.current_price;

        }
        catch (Exception e) {
            throw new ProductNotFoundException();
        }


        return product;
    }

    /**
     * Stores product info in a NoSQL database.
     * Accepts a json request body in the following format
     * {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
     * @param prodInfo Product info JSON request body
     * @param productId Id of product that need to be stored.
     * @return
     * @throws ProductMisMatchException
     */
    @ApiOperation(value = "Modifies the product information")
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "Created"),
                    @ApiResponse(code = 400, message = "ProductId in request header and body doesn't match")})
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String modifyPrice(@RequestBody Product prodInfo, @PathVariable ("id") String productId) throws ProductMisMatchException
    {
        if (!prodInfo.productId.equalsIgnoreCase(productId)) {
            throw new ProductMisMatchException();
        }
        repository.save(prodInfo);
        return "{\"response\":\"success\"}";
    }

}