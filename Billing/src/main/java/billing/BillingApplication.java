package billing;

import billing.Entities.Bill;
import billing.Entities.ProductItem;
import billing.Feign.CustomerRestClient;
import billing.Feign.ProductItemRestClient;
import billing.model.Customer;
import billing.model.Product;
import billing.repositories.BillRepository;
import billing.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient){

        return args -> {
            Customer customer = customerRestClient.getCutomerById(1L);
            Bill bill1 = billRepository.save(new Bill(null,new Date(),null,customer.getId(),customer));
            PagedModel<Product> products = productItemRestClient.pageProducts();
            products.forEach(p->{
                ProductItem productItem = new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+new Random().nextInt(100));
                productItem.setBill(bill1);
                productItemRepository.save(productItem);
            });
        };
    }

}
