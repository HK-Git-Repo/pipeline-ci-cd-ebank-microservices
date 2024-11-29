package kad.dev.accountservice.web;

import kad.dev.accountservice.entity.BankAccount;
import kad.dev.accountservice.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class BankAccountController {
    private final BankAccountRepository repository;

    @GetMapping("/accounts")
    public List<BankAccount> allAccount() {
        return repository.findAll();
    }

    @GetMapping("/account/{id}")
    public BankAccount accountById(@PathVariable String id) {
        return repository.findById(id).get();
    }
}
