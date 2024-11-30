package kad.dev.accountservice.web;

import kad.dev.accountservice.dto.BankAccountDTO;
import kad.dev.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountController {
    private final AccountService service;

    @PostMapping("/save-account")
    public BankAccountDTO newAccount(@RequestBody BankAccountDTO accountDTO) {
        return service.createNewAccount(accountDTO);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> allAccounts() {
        return service.getAllAccounts();
    }

    @GetMapping("/accountsByFilter")
    public List<BankAccountDTO> allAccountsByType(@RequestParam String type) {
        return service.getAccountsByType(type.toUpperCase());
    }

    @GetMapping("/accountsByCustomer/{id}")
    public List<BankAccountDTO> allAccountsByCustomerId(@PathVariable Long id) {
        return service.getAccountsByCustomerId(id);
    }

    @GetMapping("/account/{id}")
    public BankAccountDTO accountById(@PathVariable String id) {
        return service.getAccountById(id);
    }

    @PutMapping("/update-account/{id}")
    public BankAccountDTO updateAccount( @PathVariable String id, @RequestBody BankAccountDTO dto ) {
        return service.updateAccount(id, dto);
    }

    @DeleteMapping("delete-account/{id}")
    public void deleteAccount(@PathVariable String id) {
        service.deleteAccount(id);
    }

}
