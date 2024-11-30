package kad.dev.accountservice;

import kad.dev.accountservice.api.CustomerServiceClient;
import kad.dev.accountservice.entity.BankAccount;
import kad.dev.accountservice.enums.AccountType;
import kad.dev.accountservice.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository repository, CustomerServiceClient customerServiceClient) {
        return args -> {
            customerServiceClient.getAllCustomers().forEach(c -> {
                List<BankAccount> accounts = List.of(
                        BankAccount.builder()
                                .accountId(UUID.randomUUID().toString())
                                .balance(50000)
                                .currency("MAD")
                                .type(AccountType.CURRENT_ACCOUNT)
                                .customerId(c.getId())
                                .customer(c)
                                .createdAt(LocalDate.now())
                                .build(),
                        BankAccount.builder()
                                .accountId(UUID.randomUUID().toString())
                                .balance(34000)
                                .currency("MAD")
                                .type(AccountType.SAVING_ACCOUNT)
                                .customerId(c.getId())
                                .customer(c)
                                .createdAt(LocalDate.now())
                                .build()
                );
                repository.saveAll(accounts);
            });
        };
    }
}
