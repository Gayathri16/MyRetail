#MyRetail REST API

This API provides users ability to store and retrieve and store information from My Retail NoSQL database.

Instructions to setup
---------------------
1. Install MongoDB in your system.
https://docs.mongodb.com/manual/installation/
2. Run MongoDB.
3. Clone the code from git.
4. Change into MyRetail directory.
5. Then run the following command.
`./gradlew bootRun`
6. Open browser and visit
`http://localhost:8080/swagger-ui.html`
7. Swagger documentation explains the expected request and response for GET and PUT requests.

Example
-------
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
 
 

  




