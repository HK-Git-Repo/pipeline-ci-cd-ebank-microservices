package kad.dev.accountservice.entity;

import jakarta.persistence.*;
import kad.dev.accountservice.enums.TransactionType;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private double amount;
    @ManyToOne
    private BankAccount bankAccount;
}
