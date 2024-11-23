package kad.dev.customerservice.mapper;

import kad.dev.customerservice.dtos.CustomerDTO;
import kad.dev.customerservice.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@ActiveProfiles("test")
public class CustomerMapperTest {
    CustomerMapper underTest = new CustomerMapper();

    /*
     * The given Object or Data Structure should be found
     */

    @Test
    void shouldMapCustomerToCustomerDTO() {
        Customer customer = Customer.builder()
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
        CustomerDTO target = underTest.fromCustomer(customer);
        assertThat(target).isNotNull();
        assertThat(target).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldMapCustomerDTOToCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(1L)
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();
        Customer expected = Customer.builder()
                .id(1L)
                .firstName("hamza")
                .lastName("elkaddari")
                .email("hamza@gmail.com")
                .build();
        Customer target = underTest.fromCustomerDTO(customerDTO);
        assertThat(target).isNotNull();
        assertThat(target).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldMapListOfCustomersToListOfCustomersDTO() {
        List<Customer> givenCustomers = List.of(
                Customer.builder()
                        .id(1L)
                        .firstName("hamza")
                        .lastName("elkaddari")
                        .email("hamza@gmail.com")
                        .build(),
                Customer.builder()
                        .id(1L)
                        .firstName("yassin")
                        .lastName("yassin")
                        .email("yassin@gmail.com")
                        .build()
        );
        List<CustomerDTO> expected = List.of(
                CustomerDTO.builder()
                        .id(1L)
                        .firstName("hamza")
                        .lastName("elkaddari")
                        .email("hamza@gmail.com")
                        .build(),
                CustomerDTO.builder()
                        .id(1L)
                        .firstName("yassin")
                        .lastName("yassin")
                        .email("yassin@gmail.com")
                        .build()
        );
        List<CustomerDTO> result = underTest.fromListCustomers(givenCustomers);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldMapListOfCustomersDtoToListOfCustomers() {
        List<CustomerDTO> givenCustomersDTO = List.of(
                CustomerDTO.builder()
                        .id(1L)
                        .firstName("hamza")
                        .lastName("elkaddari")
                        .email("hamza@gmail.com")
                        .build(),
                CustomerDTO.builder()
                        .id(1L)
                        .firstName("yassin")
                        .lastName("yassin")
                        .email("yassin@gmail.com")
                        .build()
        );
        List<Customer> expected = List.of(
                Customer.builder()
                        .id(1L)
                        .firstName("hamza")
                        .lastName("elkaddari")
                        .email("hamza@gmail.com")
                        .build(),
                Customer.builder()
                        .id(1L)
                        .firstName("yassin")
                        .lastName("yassin")
                        .email("yassin@gmail.com")
                        .build()
        );
        List<Customer> result = underTest.fromListCustomersDTO(givenCustomersDTO);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    /*
    * The given Object or Data Structure should not be found
    */

    @Test
    void shouldNotMapNullCustomerToCustomerDTO() {
        Customer customer = null;
        assertThatThrownBy(()-> underTest.fromCustomer(customer)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotMapNullListOfCustomerToListOfCustomerDTO() {
        List<Customer> customers = null;
        assertThatThrownBy(()-> underTest.fromListCustomers(customers)).isInstanceOf(NullPointerException.class);
    }
}
