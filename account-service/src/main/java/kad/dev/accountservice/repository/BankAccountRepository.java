package kad.dev.accountservice.repository;

import kad.dev.accountservice.entity.BankAccount;
import kad.dev.accountservice.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByType(AccountType type);
    List<BankAccount> findByCustomerId(Long id);
}
