package billing.Web;

import billing.Entities.Bill;
import billing.Feign.CustomerRestClient;
import billing.Feign.ProductItemRestClient;
import billing.model.Customer;
import billing.model.Product;
import billing.repositories.BillRepository;
import billing.repositories.ProductItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BillingRestController {

    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCutomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(p->{
            Product product = productItemRestClient.getProductById(p.getProductID());
            p.setProduct(product);
        });
        return bill ;
    }
}
