package myretail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Gayathri on 11/7/16.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "ProductId in request header and body doesn't match.")
public class ProductMisMatchException extends RuntimeException{
}
