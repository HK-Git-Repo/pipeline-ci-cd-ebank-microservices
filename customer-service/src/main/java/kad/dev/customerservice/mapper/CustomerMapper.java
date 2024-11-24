package kad.dev.customerservice.mapper;

import jakarta.validation.constraints.Null;
import kad.dev.customerservice.dtos.CustomerDTO;
import kad.dev.customerservice.entities.Customer;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {
    private final ModelMapper underClass = new ModelMapper();

    public CustomerDTO fromCustomer(Customer customer) throws IllegalArgumentException {
        return underClass.map(customer, CustomerDTO.class);
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO)throws IllegalArgumentException {
        return underClass.map(customerDTO, Customer.class);
    }

    public List<CustomerDTO> fromListCustomers(List<Customer> customers) throws NullPointerException {
        return customers.stream().map(c-> underClass.map(c, CustomerDTO.class)).collect(Collectors.toList());
    }

    public List<Customer> fromListCustomersDTO(List<CustomerDTO> customersDTO) throws NullPointerException {
        return customersDTO.stream().map(c-> underClass.map(c, Customer.class)).collect(Collectors.toList());
    }
}
