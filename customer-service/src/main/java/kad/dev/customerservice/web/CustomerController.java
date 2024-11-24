package kad.dev.customerservice.web;

import jakarta.validation.Valid;
import kad.dev.customerservice.dtos.CustomerDTO;
import kad.dev.customerservice.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CustomerController {
    private CustomerService service;

    @PostMapping("/save-customer")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO save(@RequestBody @Valid CustomerDTO customerDTO){
        return service.saveNewCustomer(customerDTO);
    }

    @PutMapping("/update-customer/{id}")
    public CustomerDTO update(@PathVariable Long id, @RequestBody @Valid CustomerDTO customerDTO){
        return service.updateCustomer(id, customerDTO);
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getById(@PathVariable Long id) {
        return service.findCustomerById(id);
    }

    @GetMapping("/customers")
    public List<CustomerDTO> getAll() {
        return service.findAllCustomers();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam String keyword) {
        return service.searchCustomers(keyword);
    }

    @DeleteMapping("/delete-customer/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCustomer(id);
    }

}
