package myretail.repository;

import myretail.data.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Gayathri on 11/7/16.
 */
public interface ProductRepository extends MongoRepository<Product, String> {
    public Product findByProductId(String productId);
}
