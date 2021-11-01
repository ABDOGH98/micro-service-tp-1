package customer.DTO;

import customer.Entities.Customer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "fullCustomer",types = Customer.class)
public interface CustometDTO extends Projection {
    public Long getId();
    public String getEmail();
    public String getName();

}
