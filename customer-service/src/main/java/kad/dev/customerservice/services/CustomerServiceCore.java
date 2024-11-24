package kad.dev.customerservice.services;

import kad.dev.customerservice.dtos.CustomerDTO;
import kad.dev.customerservice.entities.Customer;
import kad.dev.customerservice.exceptions.CustomerNotFoundException;
import kad.dev.customerservice.exceptions.EmailAlreadyExistException;
import kad.dev.customerservice.mapper.CustomerMapper;
import kad.dev.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceCore implements CustomerService{

    private final CustomerRepository repository;
    private final CustomerMapper mapper = new CustomerMapper();

    public CustomerServiceCore(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) throws EmailAlreadyExistException {
        Optional<Customer> email = repository.findByEmail(customerDTO.getEmail());
        if( email.isPresent() ) throw new EmailAlreadyExistException();
        Customer customer = mapper.fromCustomerDTO(customerDTO);
        Customer saved = repository.save(customer);
        return mapper.fromCustomer(saved);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws CustomerNotFoundException {
        Optional<Customer> customer = repository.findById(id);
        if( customer.isEmpty() ) throw new CustomerNotFoundException();
        customerDTO.setId(id);
        Customer customerToUpdate = mapper.fromCustomerDTO(customerDTO);
        Customer updated = repository.save(customerToUpdate);
        return mapper.fromCustomer(updated);
    }

    @Override
    public CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer = repository.findById(id);
        if(customer.isEmpty()) throw new CustomerNotFoundException();
        return mapper.fromCustomer(customer.get());
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        List<Customer> customers = repository.findAll();
        return mapper.fromListCustomers(customers);
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers = repository.findByFirstNameContainsIgnoreCase(keyword);
        return mapper.fromListCustomers(customers);
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer = repository.findById(id);
        if( customer.isEmpty() ) throw new CustomerNotFoundException();
        repository.deleteById(id);
    }
}
