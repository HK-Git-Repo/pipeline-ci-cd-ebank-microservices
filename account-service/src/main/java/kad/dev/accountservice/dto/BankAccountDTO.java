package kad.dev.accountservice.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kad.dev.accountservice.enums.AccountType;
import kad.dev.accountservice.model.Customer;
import lombok.*;

import java.time.LocalDate;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class BankAccountDTO {
    private String accountId;
    private double balance;
    @NotNull
    private String currency;
    @NotNull
    private AccountType type;
    @NotNull
    private Long customerId;
    private LocalDate createdAt;
}
