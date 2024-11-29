package kad.dev.accountservice.entity;

import jakarta.persistence.*;
import kad.dev.accountservice.enums.AccountType;
import kad.dev.accountservice.model.Customer;
import lombok.*;

import java.time.LocalDate;
@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class BankAccount {
    @Id
    private String accountId;
    private double balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @Transient
    private Customer customer;
    private Long customerId;
    private LocalDate createdAt;
}
