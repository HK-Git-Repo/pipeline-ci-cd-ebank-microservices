package kad.dev.customerservice.integrationTest;

import kad.dev.customerservice.dtos.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Slf4j
public class CustomerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<CustomerDTO> customers = List.of(
            CustomerDTO.builder()
                    .id(1L)
                    .firstName("hamza")
                    .lastName("hamza1")
                    .email("h1@gmail.com")
                    .build(),
            CustomerDTO.builder()
                    .id(2L)
                    .firstName("mouhssine")
                    .lastName("mouhssine1")
                    .email("m1@gmail.com")
                    .build()
    );

    @Test
    void shouldSaveValidCustomer(){
            ResponseEntity<CustomerDTO> response = restTemplate.exchange("/api/save-customer", HttpMethod.POST, new HttpEntity<>(this.customers.get(0)), CustomerDTO.class);
            AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            AssertionsForClassTypes.assertThat(response.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(this.customers.get(0));

    }

    @Test
    void shouldGetAllCustomers() {
        this.customers.forEach(e ->  restTemplate.exchange("/api/save-customer", HttpMethod.POST, new HttpEntity<>(e), CustomerDTO.class)
            );
        ResponseEntity<CustomerDTO[]> response = restTemplate.exchange("/api/customers", HttpMethod.GET, new HttpEntity<>(null), CustomerDTO[].class);
        List<CustomerDTO> content = Arrays.asList(response.getBody());
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(content.size()).isEqualTo(2);
        AssertionsForClassTypes.assertThat(content).usingRecursiveComparison().isEqualTo(this.customers);
    }

    @Test
    void shouldSearchCustomersByFirstName(){
        String keyword="h";
        ResponseEntity<CustomerDTO[]> response = restTemplate.exchange("/api/customers/search?keyword="+keyword, HttpMethod.GET, new HttpEntity<>(null), CustomerDTO[].class);
        List<CustomerDTO> content = Arrays.asList(response.getBody());
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(content.size()).isEqualTo(2);
        List<CustomerDTO> expected = customers.stream().filter(c -> c.getFirstName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
        AssertionsForClassTypes.assertThat(content).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldGetCustomerById(){
        Long id = 1L;
        ResponseEntity<CustomerDTO> response = restTemplate.exchange("/api/customer/"+id, HttpMethod.GET, new HttpEntity<>(null), CustomerDTO.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(response.getBody()).isNotNull();
        AssertionsForClassTypes.assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(customers.get(0));
    }
    @Test
    void shouldNotFindCustomerById(){
        Long id = 9L;
        ResponseEntity<CustomerDTO> response = restTemplate.exchange("/api/customer/"+id, HttpMethod.GET, new HttpEntity<>(null), CustomerDTO.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    void shouldNotSaveInvalidCustomer() throws IOException {
        CustomerDTO customerDTO = CustomerDTO.builder().firstName("").lastName("").email("").build();
        ResponseEntity<String> response = restTemplate.exchange("/api/save-customer", HttpMethod.POST, new HttpEntity<>(customerDTO), String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<String, ArrayList<String>> errors = objectMapper.readValue(response.getBody(), HashMap.class);
        AssertionsForClassTypes.assertThat(errors.keySet().size()).isEqualTo(3);
        AssertionsForClassTypes.assertThat(errors.get("firstName").size()).isEqualTo(2);
        AssertionsForClassTypes.assertThat(errors.get("lastName").size()).isEqualTo(2);
        AssertionsForClassTypes.assertThat(errors.get("email").size()).isEqualTo(2);
    }

    @Test
    @Rollback
    void shouldUpdateValidCustomer(){
        Long id = 2L;
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(this.customers.get(1).getId()).firstName("mouhssine").lastName("mouhssine").email("mouhssine@gmail.com").build();
        ResponseEntity<CustomerDTO> response = restTemplate.exchange("/api/update-customer/"+id, HttpMethod.PUT, new HttpEntity<>(customerDTO), CustomerDTO.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(customerDTO);
    }
    @Test
    @Rollback
    void shouldDeleteCustomer(){
        Long id = 1L;
        ResponseEntity<String> response = restTemplate.exchange("/api/delete-customer/"+id, HttpMethod.DELETE, new HttpEntity<>(null), String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}



