package kad.dev.customerservice.services;

import kad.dev.customerservice.dtos.CustomerDTO;
import kad.dev.customerservice.exceptions.CustomerNotFoundException;
import kad.dev.customerservice.exceptions.EmailAlreadyExistException;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveNewCustomer(CustomerDTO customerDTO) throws EmailAlreadyExistException;
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws CustomerNotFoundException;
    CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException;
    List<CustomerDTO> findAllCustomers();
    List<CustomerDTO> searchCustomers(String keyword);
    void deleteCustomer(Long id) throws CustomerNotFoundException;
}
