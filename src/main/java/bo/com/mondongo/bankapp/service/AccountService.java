package bo.com.mondongo.bankapp.service;

import bo.com.mondongo.bankapp.dto.AccountSampleDto;
import bo.com.mondongo.bankapp.entity.Account;
import bo.com.mondongo.bankapp.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity create(Account account) {
        Account resultAccount = accountRepository.save(account);
        Map<String, Object> result = new HashMap<>();
        result.put("id", resultAccount.getId());
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    public List<AccountSampleDto> listAll() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountSampleDto> accountSampleDtos = new ArrayList<>();
        for (Account account : accounts) {
            accountSampleDtos.add(new AccountSampleDto(account.getId(), account.getNumber()));
        }
        return accountSampleDtos;
    }
}
