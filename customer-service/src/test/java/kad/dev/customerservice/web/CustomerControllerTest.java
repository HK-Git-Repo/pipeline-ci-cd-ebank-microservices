package kad.dev.customerservice.web;

import jakarta.ws.rs.core.MediaType;
import kad.dev.customerservice.dtos.CustomerDTO;
import kad.dev.customerservice.entities.Customer;
import kad.dev.customerservice.exceptions.CustomerNotFoundException;
import kad.dev.customerservice.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

    @MockBean
    private CustomerService service;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    List<CustomerDTO> customers = List.of();
    @BeforeEach
    void setUp() {
        this.customers = List.of(
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
    }

    @Test
    void shouldGetAllCustomers() throws Exception {
        Mockito.when(service.findAllCustomers()).thenReturn(this.customers);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(this.customers)));
    }

    @Test
    void shouldGetCustomerById() throws Exception {
        Long id = 1L;
        Mockito.when(service.findCustomerById(id)).thenReturn(this.customers.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(this.customers.get(0))));
    }

    @Test
    void shouldNotGetCustomerByInvalidId() throws Exception {
        Long id = 5L;
        Mockito.when(service.findCustomerById(id)).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void shouldSaveCustomer() throws Exception {
        String expected = """
                { "id" : 1, "firstName" : "hamza", "lastName" : "hamza1", "email" : "h1@gmail.com" }
                """;
        Mockito.when(service.saveNewCustomer(Mockito.any())).thenReturn(this.customers.get(0));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/save-customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.customers.get(0)))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void searchCustomers() throws Exception {
        String keyword="h";
        Mockito.when(service.findAllCustomers()).thenReturn(this.customers);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers?keyword="+keyword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(this.customers)));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        Long id=1L;
        Mockito.when(service.updateCustomer(Mockito.eq(id),Mockito.any())).thenReturn(this.customers.get(0));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/update-customer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.customers.get(0))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(this.customers.get(0))));
    }
    @Test
    void shouldDeleteCustomer() throws Exception {
        Long id=1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete-customer/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
