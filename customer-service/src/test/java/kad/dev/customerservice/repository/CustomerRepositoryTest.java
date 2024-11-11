package kad.dev.customerservice.repository;

import kad.dev.customerservice.entities.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Testcontainers // Unit Test using Postgres inside Docker container
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Unit Test using Postgres inside Docker container
class CustomerRepositoryTest {
    @Container
    @ServiceConnection
     static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14"))
            .withDatabaseName("test")
            .withUsername("root")
            .withPassword("root");
    @Autowired
    private CustomerRepository customerRepository;

    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.close();
    }

    @Test
    @Order(1)
    void test(){
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

   @BeforeEach
    void setUp() {
        List<Customer> customers = List.of(
                Customer.builder()
                        .firstName("Hamza")
                        .lastName("ELKADDARI")
                        .email("kad@gmail.com")
                        .build(),
                Customer.builder()
                        .firstName("Amal")
                        .lastName("benani")
                        .email("amal@gmail.com")
                        .build(),
                Customer.builder()
                        .firstName("Mohammed")
                        .lastName("imame")
                        .email("med@gmail.com")
                        .build()
        );
        customerRepository.saveAll(customers);
    }

    @AfterEach
    void clear() {
        customerRepository.deleteAll();
    }

    @Test
    void shouldFindByEmail() {
        String email = "kad@gmail.com";
        Optional<Customer> byEmail = customerRepository.findByEmail(email);
        assertThat(byEmail).isPresent();
    }

    @Test
    void shouldNotFindByEmail() {
        String email = "xxxx@xxxx@@gmail.com";
        Optional<Customer> byEmail = customerRepository.findByEmail(email);
        assertThat(byEmail).isEmpty();
    }

    @Test
    void shouldFindCustomersByFirstName() {
        String keyword = "h";
        List<Customer> target = List.of(
                Customer.builder()
                        .firstName("Hamza")
                        .lastName("ELKADDARI")
                        .email("kad@gmail.com")
                        .build(),
                Customer.builder()
                        .firstName("Mohammed")
                        .lastName("imame")
                        .email("med@gmail.com")
                        .build()
        );
        List<Customer> myList = customerRepository.findByFirstNameContainsIgnoreCase(keyword);
        assertThat(myList).isNotNull();
        assertThat(myList.size()).isEqualTo(target.size());
        assertThat(myList).usingRecursiveComparison().ignoringFields("id").isEqualTo(target);
    }
}