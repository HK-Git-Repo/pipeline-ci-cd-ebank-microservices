package kad.dev.customerservice;

import kad.dev.customerservice.entities.Customer;
import kad.dev.customerservice.mapper.CustomerMapper;
import kad.dev.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            List<Customer> customers = List.of(
                    Customer.builder()
                            .id(1L)
                            .firstName("Hamza")
                            .lastName("ELKADDARI")
                            .email("kad@gmail.com")
                            .build(),
                    Customer.builder()
                            .id(2L)
                            .firstName("Amal")
                            .lastName("benani")
                            .email("amal@gmail.com")
                            .build(),
                    Customer.builder()
                            .id(3L)
                            .firstName("Mohammed")
                            .lastName("imame")
                            .email("med@gmail.com")
                            .build()
            );
            customerRepository.saveAll(customers);
        };
    }

}
