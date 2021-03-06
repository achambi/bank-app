package bo.com.mondongo.bankapp.service;

import bo.com.mondongo.bankapp.dto.AccountSampleDto;
import bo.com.mondongo.bankapp.entity.Account;
import bo.com.mondongo.bankapp.entity.Currency;
import bo.com.mondongo.bankapp.entity.Department;
import bo.com.mondongo.bankapp.repository.AccountRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void create() {
        Account account = new Account();
        account.setNumber("201-01-000001");
        account.setDepartment(Department.LA_PAZ);
        account.setBalance(0.00);
        account.setCurrency(Currency.BOLIVIANOS);
        account.setHolder("Jhon Snow");
        account.setId(1);
        when(accountRepository.save(eq(account))).thenReturn(account);

        ResponseEntity responseEntity = accountService.create(account);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Map result = (HashMap) responseEntity.getBody();
        assertEquals(account.getId(), result.get("id"));
        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void list() {
        Account account1 = new Account();
        account1.setNumber("201-01-000001");
        account1.setId(1);

        Account account2 = new Account();
        account2.setNumber("201-01-000002");
        account2.setId(2);

        List<Account> accountsExpected = new ArrayList<>();
        accountsExpected.add(account1);
        accountsExpected.add(account2);
        Mockito.when(accountRepository.findAll()).thenReturn(accountsExpected);


        List<AccountSampleDto> accounts = accountService.listAll();
        assertEquals(accountsExpected.size(), accounts.size());
        for (int i = 0; i < accounts.size(); i++) {
            assertEquals(accountsExpected.get(i).getId(), accounts.get(i).getId());
            assertEquals(accountsExpected.get(i).getNumber(), accounts.get(i).getNumber());
        }
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(accountRepository);
    }
}