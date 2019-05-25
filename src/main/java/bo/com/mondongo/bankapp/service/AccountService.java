package bo.com.mondongo.bankapp.service;

import bo.com.mondongo.bankapp.dto.AccountSampleDto;
import bo.com.mondongo.bankapp.entity.Account;
import bo.com.mondongo.bankapp.entity.Movement;
import bo.com.mondongo.bankapp.repository.AccountRepository;
import bo.com.mondongo.bankapp.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private MovementRepository movementRepository;

    @Autowired
    public AccountService(@Qualifier("AccountRepository") AccountRepository accountRepository, @Qualifier("MovementRepository") MovementRepository movementRepository) {
        this.accountRepository = accountRepository;
        this.movementRepository = movementRepository;
    }

    public ResponseEntity create(Account account) {
        Map<String, Object> result = new HashMap<>();
        Account resultAccount = accountRepository.save(account);
        result.put("id", resultAccount.getId());

        if (account.getBalance() > 0) {
            Movement movement = Movement.createCredit(account);
            movement = movementRepository.save(movement);
            result.put("movementId", movement.getId());
        }

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
