package kad.dev.accountservice.api;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import kad.dev.accountservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerServiceClient {
    @GetMapping("/customer/{id}")
    @CircuitBreaker(name = "CustomerService", fallbackMethod = "getDefaultCustomer")
    Customer getCustomerById(@PathVariable Long id);

    @GetMapping("/customers")
    @CircuitBreaker(name = "CustomerService", fallbackMethod = "getDefaultCustomers")
    List<Customer> getAllCustomers();
    /*
     * Default Circuit Breakers Methods Definitions
     */
    default Customer getDefaultCustomer(Long id, Exception e) {
        return Customer.builder()
                .id(id)
                .firstName("Not Available")
                .lastName("Not Available")
                .email("Not Available")
                .build();
    }
    default List<Customer> getDefaultCustomers(Exception e) {
        return List.of();
    }
}













