package myretail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import myretail.data.Product;
import myretail.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class Application {

    @Autowired
    private ProductRepository repository;


    /**
     * This method starts the application
     * @param args
     */
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    /**
     * Inserting sample data into the MongoDB database
     */
    @PostConstruct
    public void storeSampleData() {
        Map<String, String> map = new HashMap<>();
        map.put("value", "13.49");
        map.put("currency_code", "USD");
        repository.save(new Product("13860428", "The Big Lebowski (Blu-ray) (Widescreen)", map));

        map.put("value", "500");
        map.put("currency_code", "USD");
        repository.save(new Product("15117729", "Apple IPAD", map));
    }

    /**
     * Initialize Swagger documentation
     * @return
     */
    @Bean
    public Docket productApi() {
        ApiInfo apiInfo =
                new ApiInfo("My Retail API", "API to store and retrieve data from MyRetail", "1",
                        "http://terms-of-service.url",
                        "Gayathri", "License", "http://licenseurl");
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/products.*"))
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false);
    }
}