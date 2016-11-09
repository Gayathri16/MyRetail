package myretail.data;

import org.springframework.data.annotation.Id;

import java.util.Map;

/**
 * Created by Gayathri on 11/7/16.
 */
public class Product {

    @Id
    public String productId;
    public String title;
    public Map<String, String> current_price;

    public Product() {}

    public Product(String productId, String title, Map<String,String> current_price) {
        this.productId = productId;
        this.title = title;
        this.current_price = current_price;
    }

    public String getProductId() {
        return productId;
    }

    public void setId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Map<String, String> current_price) {
        this.current_price = current_price;
    }


}
