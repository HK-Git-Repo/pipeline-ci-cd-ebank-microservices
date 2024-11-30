package kad.dev.accountservice.service;

import kad.dev.accountservice.api.CustomerServiceClient;
import kad.dev.accountservice.dto.BankAccountDTO;
import kad.dev.accountservice.entity.BankAccount;
import kad.dev.accountservice.enums.AccountType;
import kad.dev.accountservice.mapper.BankAccountMapper;
import kad.dev.accountservice.model.Customer;
import kad.dev.accountservice.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceCore implements AccountService {
    private final BankAccountRepository repository;
    private final CustomerServiceClient customerServiceClient;
    private final BankAccountMapper mapper;
    private final double MIN_BALANCE = 100;

    @Override
    public BankAccountDTO createNewAccount(BankAccountDTO dto) {
        Customer customer = customerServiceClient.getCustomerById(dto.getCustomerId());
        if( customer == null ) throw new RuntimeException("Customer Not Found");
        if( dto.getType() == AccountType.SAVING_ACCOUNT ) {
            if( dto.getBalance() < MIN_BALANCE ) throw new RuntimeException("The balance for a SAVING_ACCOUNT must be at least 100");
        }
        BankAccount accountToSave = mapper.fromBankAccountDTO(dto);
        accountToSave.setAccountId(UUID.randomUUID().toString());
        accountToSave.setCreatedAt(LocalDate.now());
        BankAccount saved = repository.save(accountToSave);
        return mapper.fromBankAccount(saved);
    }

    @Override
    public List<BankAccountDTO> getAllAccounts() {
        List<BankAccount> accounts = repository.findAll().stream().peek(ba -> {
            Customer customer = customerServiceClient.getCustomerById(ba.getCustomerId());
            ba.setCustomer(customer);
        }).collect(Collectors.toList());
        return mapper.fromListBankAccounts(accounts);
    }

    @Override
    public List<BankAccountDTO> getAccountsByType(String type) {
        List<BankAccount> accounts = repository.findByType(AccountType.valueOf(type)).stream().peek(ba -> {
            Customer customer = customerServiceClient.getCustomerById(ba.getCustomerId());
            ba.setCustomer(customer);
        }).collect(Collectors.toList());
        return mapper.fromListBankAccounts(accounts);
    }

    @Override
    public List<BankAccountDTO> getAccountsByCustomerId(Long id) {
        List<BankAccount> accountList = repository.findByCustomerId(id);
        return mapper.fromListBankAccounts(accountList);
    }

    @Override
    public BankAccountDTO getAccountById(String id) {
        BankAccount account = repository.findById(id).get();
        Customer customer = customerServiceClient.getCustomerById(account.getCustomerId());
        account.setCustomer(customer);
        return mapper.fromBankAccount(account);
    }

    @Override
    public BankAccountDTO updateAccount(String id, BankAccountDTO dto) {
        Customer customer = customerServiceClient.getCustomerById(dto.getCustomerId());
        if( customer == null ) throw new RuntimeException("Customer Not Found");
        if( dto.getType() == AccountType.SAVING_ACCOUNT ) {
            if( dto.getBalance() < MIN_BALANCE ) throw new RuntimeException("The balance for a SAVING_ACCOUNT must be at least 100");
        }
        BankAccount accountToUpdate = mapper.fromBankAccountDTO(dto);
        BankAccount updated = repository.save(accountToUpdate);
        return mapper.fromBankAccount(updated);
    }

    @Override
    public void deleteAccount(String id) {
        repository.deleteById(id);
    }
}
