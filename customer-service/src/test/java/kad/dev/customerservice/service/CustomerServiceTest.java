package kad.dev.customerservice.service;

import kad.dev.customerservice.dtos.CustomerDTO;
import kad.dev.customerservice.entities.Customer;
import kad.dev.customerservice.exceptions.CustomerNotFoundException;
import kad.dev.customerservice.exceptions.EmailAlreadyExistException;
import kad.dev.customerservice.mapper.CustomerMapper;
import kad.dev.customerservice.repository.CustomerRepository;
import kad.dev.customerservice.services.CustomerServiceCore;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository repository;
    @Mock
    private CustomerMapper mapper;
    @InjectMocks
    private CustomerServiceCore service;

    @Test
    void shouldSaveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();
        Customer customer = Customer.builder()
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();
        Customer savedCustomer = Customer.builder()
                .id(1L)
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();
        CustomerDTO expected = CustomerDTO.builder()
                .id(1L)
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();


        Mockito.when(repository.findByEmail(customerDTO.getEmail())).thenReturn(Optional.empty());
        Mockito.when(mapper.fromCustomerDTO(customerDTO)).thenReturn(customer);
        Mockito.when(repository.save(customer)).thenReturn(savedCustomer);
        Mockito.when(mapper.fromCustomer(savedCustomer)).thenReturn(expected);

        CustomerDTO savedNewCustomer = service.saveNewCustomer(customerDTO);
        AssertionsForClassTypes.assertThat(savedNewCustomer).isNotNull();
        AssertionsForClassTypes.assertThat(savedNewCustomer).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldNotSaveNewCustomerWithEmailExist() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();
        Customer customer = Customer.builder()
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();


        Mockito.when(repository.findByEmail(customerDTO.getEmail())).thenReturn(Optional.of(customer));

        AssertionsForClassTypes.assertThatThrownBy(()-> service.saveNewCustomer(customerDTO)).isInstanceOf(EmailAlreadyExistException.class);
    }

    @Test
    void shouldUpdateCustomer() {
        Long id = 1L;
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza.el@gmail.com")
                .build();
        Customer searchedCustomer = Customer.builder()
                .id(1L)
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();
        Customer mappedCustomer = Customer.builder()
                .id(1L)
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza.el@gmail.com")
                .build();
        Customer updatedCustomer = Customer.builder()
                .id(1L)
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza.el@gmail.com")
                .build();
        CustomerDTO expected = CustomerDTO.builder()
                .id(1L)
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza.el@gmail.com")
                .build();


        Mockito.when(repository.findById(id)).thenReturn(Optional.of(searchedCustomer));
        Mockito.when(mapper.fromCustomerDTO(customerDTO)).thenReturn(mappedCustomer);
        Mockito.when(repository.save(mappedCustomer)).thenReturn(updatedCustomer);
        Mockito.when(mapper.fromCustomer(updatedCustomer)).thenReturn(expected);

        CustomerDTO result = service.updateCustomer(id, customerDTO);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldNotUpdateCustomerWithNotFoundCustomer() {
        Long id = 1L;
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza.el@gmail.com")
                .build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(()-> service.updateCustomer(id, customerDTO)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void shouldDeleteCustomer() {
        Long id = 1L;
        Customer searchedCustomer = Customer.builder()
                .id(1L)
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(searchedCustomer));
        service.deleteCustomer(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    void shouldNotDeleteCustomer() {
        Long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(()-> service.deleteCustomer(id)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void shouldFindAllCustomers() {
        List<Customer> customers = List.of(
                Customer.builder()
                        .id(1L)
                        .firstName("hamza")
                        .lastName("elkaddari")
                        .email("hamza.el@gmail.com")
                        .build(),
                Customer.builder()
                        .id(2L)
                        .firstName("mouhssine")
                        .lastName("mouhssine1")
                        .email("m1@gmail.com")
                        .build()
        );
        List<CustomerDTO> expectedList = List.of(
                CustomerDTO.builder()
                        .id(1L)
                        .firstName("hamza")
                        .lastName("elkaddari")
                        .email("hamza.el@gmail.com")
                        .build(),
                CustomerDTO.builder()
                        .id(2L)
                        .firstName("mouhssine")
                        .lastName("mouhssine1")
                        .email("m1@gmail.com")
                        .build()
        );

        Mockito.when(repository.findAll()).thenReturn(customers);
        Mockito.when(mapper.fromListCustomers(customers)).thenReturn(expectedList);

        List<CustomerDTO> allCustomers = service.findAllCustomers();

        AssertionsForClassTypes.assertThat(expectedList).usingRecursiveComparison().isEqualTo(allCustomers);
    }

    @Test
    void shouldSearchCustomers() {
        String keyword = "h";
        List<Customer> customers = List.of(
                Customer.builder()
                        .id(1L)
                        .firstName("hamza")
                        .lastName("elkaddari")
                        .email("hamza.el@gmail.com")
                        .build(),
                Customer.builder()
                        .id(2L)
                        .firstName("mouhssine")
                        .lastName("mouhssine1")
                        .email("m1@gmail.com")
                        .build()
        );
        List<CustomerDTO> expectedList = List.of(
                CustomerDTO.builder()
                        .id(1L)
                        .firstName("hamza")
                        .lastName("elkaddari")
                        .email("hamza.el@gmail.com")
                        .build(),
                CustomerDTO.builder()
                        .id(2L)
                        .firstName("mouhssine")
                        .lastName("mouhssine1")
                        .email("m1@gmail.com")
                        .build()
        );

        Mockito.when(repository.findByFirstNameContainsIgnoreCase(keyword)).thenReturn(customers);
        Mockito.when(mapper.fromListCustomers(customers)).thenReturn(expectedList);

        List<CustomerDTO> searchedCustomers = service.searchCustomers(keyword);

        AssertionsForClassTypes.assertThat(expectedList).usingRecursiveComparison().isEqualTo(searchedCustomers);
    }
}
