package kad.dev.accountservice.service;

import kad.dev.accountservice.dto.BankAccountDTO;

import java.util.List;

public interface AccountService {
    /**
     * Create
     */
    BankAccountDTO createNewAccount(BankAccountDTO dto);
    /**
     * Read
     */
    List<BankAccountDTO> getAllAccounts();
    List<BankAccountDTO> getAccountsByType(String type);
    List<BankAccountDTO> getAccountsByCustomerId(Long id);
    BankAccountDTO getAccountById(String id);
    /**
     * Update
     */
    BankAccountDTO updateAccount(String id, BankAccountDTO dto);
    /**
     * Delete
     */
    void deleteAccount(String id);

}
