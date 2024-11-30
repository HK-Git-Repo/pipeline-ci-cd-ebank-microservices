package kad.dev.accountservice.mapper;

import kad.dev.accountservice.dto.BankAccountDTO;
import kad.dev.accountservice.entity.BankAccount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankAccountMapper {

    public BankAccountDTO fromBankAccount(BankAccount account) {
        if (account == null) {
            return null;
        }
        return BankAccountDTO.builder()
                .accountId(account.getAccountId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .type(account.getType())
                .customerId(account.getCustomerId())
                .createdAt(account.getCreatedAt())
                .build();
    }

    public BankAccount fromBankAccountDTO(BankAccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }

        return BankAccount.builder()
                .accountId(accountDTO.getAccountId())
                .balance(accountDTO.getBalance())
                .currency(accountDTO.getCurrency())
                .type(accountDTO.getType())
                .customerId(accountDTO.getCustomerId())
                .createdAt(accountDTO.getCreatedAt())
                .build();
    }

    public List<BankAccountDTO> fromListBankAccounts(List<BankAccount> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            return List.of();
        }
        return accounts.stream()
                .map(this::fromBankAccount)
                .collect(Collectors.toList());
    }

    public List<BankAccount> fromListBankAccountsDTO(List<BankAccountDTO> accountsDTO) {
        if (accountsDTO == null || accountsDTO.isEmpty()) {
            return List.of();
        }
        return accountsDTO.stream()
                .map(this::fromBankAccountDTO)
                .collect(Collectors.toList());
    }
}
