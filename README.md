#MyRetail REST API

MyRetail  API provides client application ability to:

    1. Retrieve Product and Price information by Product Id

    2. Send request to modify the price information in the database

Get Product Information:
-----------------------

Input: The client application does a GET request at the path "/products/{id}" for a product 

Internal Working: When the API receives the request, it sends a request to "redsky.target.com" and retrieves the 
product information. This product information doesn't contain price that is needed by the user. The price is retrieved
from a data store. The price information is now combined with the required product information to provide only the 
required product information to the user.

Output: For a product with product id '13860428', the sample JSON output is as shown below

{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}

Errors/Validations: Appropriate error messages are provided after validating the data. More information is available in 
the below sections. The client application can use the message in the response to display the same to the user appropriately.


Update Product Price in the datastore:
-------------------------------------

Input: The user/client application can do a PUT request with input similar to the response received in GET and should be able
to modify the price in the datastore. The request is done at the same path "/products/{id}"

Sample Input: JSON Body - {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 15.67,"currency_code":"USD"}}

Internal Working: When the API receives PUT request, it does some validations to see if the product is available. If it is, 
it ensures that the id in the URL and the JSON body is similar and if that looks same, the price for the product is modified 
in the data store.

Output: Success message is returned if the price modification is done.

Errors/Validations: Appropriate error messages are provided after validating the data. More information is available in 
the below sections. The client application can use the message in the response to display the same to the user appropriately.

Technologies Used
-----------------

1. Spring Boot - https://projects.spring.io/spring-boot/
2. MongoDB - https://www.mongodb.com/
3. Gradle - https://gradle.org/
4. Swagger - http://swagger.io/

Instructions to Setup
---------------------
1. Install MongoDB in your system - https://docs.mongodb.com/manual/installation/
2. Run MongoDB.
3. Clone the code from git.
4. Navigate to MyRetail directory.
5. Run the following command to start
`./gradlew bootRun`
6. Open browser and visit Swagger.
`http://localhost:8080/swagger-ui.html`
7. Swagger documentation explains the expected request and response for GET and PUT requests.

Testing
-------
The testcases are present in the folder 'src\test\java\myretail\controllers'. 

The test cases can be executed by running the command './gradlew test'


API Requests and Responses
--------------------------
## PUT Request:

Following PUT request will store information of productID:13860428 in NOSQL database

###Request:

`curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}} \ 
  ' 'http://localhost:8080/products/13860428'`
  
###Response:

>Status code: 201
>{
>   "response": "success"
>}
 
* When productId in request url and body is different it will return 400 Bad Request

###Request:

`curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}} \ 
  ' 'http://localhost:8080/products/15117729'`
  
###Response:

>{
>   "timestamp": 1478668717076,
>   "status": 400,
>   "error": "Bad Request",
>   "exception": "myretail.exception.ProductMisMatchException",
>   "message": "ProductId in request header and body doesn't match.",
>   "path": "/products/15117729"
>}
 
## GET Request:
 
### Request:
 
 `curl -X GET --header 'Accept: application/json' 'http://localhost:8080/products/13860428'`
 
 ### Response:
 
 >{
 >  "productId": "13860428",
 >  "title": "The Big Lebowski (Blu-ray)",
 >  "current_price": {
 >    "value": "13.49",
 >    "currency_code": "USD"
 >  }
 >}
 
 * When you give a productID that doesn't exist you will get 404 Product not found.
 
 ### Request:
 
 `curl -X GET --header 'Accept: application/json' 'http://localhost:8080/products/235345436'`
 
 ### Response:
 
 >{
   >"timestamp": 1478669015840,
   >"status": 404,
   >"error": "Not Found",
   >"exception": "myretail.exception.ProductNotFoundException",
   >"message": "Product not found",
   >"path": "/products/235345436"
 >}
 