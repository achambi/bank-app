package bo.com.mondongo.bankapp.controller;

import bo.com.mondongo.bankapp.dto.AccountInsertDTO;
import bo.com.mondongo.bankapp.dto.DTOModelMapper;
import bo.com.mondongo.bankapp.entity.Account;
import bo.com.mondongo.bankapp.entity.Currency;
import bo.com.mondongo.bankapp.entity.Department;
import bo.com.mondongo.bankapp.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import javax.ws.rs.core.MediaType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest extends TestCase {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AccountService accountService;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
            .standaloneSetup(new AccountController(accountService))
            .setCustomArgumentResolvers(new DTOModelMapper(objectMapper))
            .build();
    }

    @Test
    public void createAccount() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO();
        accountInsertDTO.setHolder("Daenerys Targaryen");
        accountInsertDTO.setBalance(20.00);
        accountInsertDTO.setDepartment(Department.BENI);
        accountInsertDTO.setCurrency(Currency.BOLIVIANOS);

        Account accountExpected = new Account(null, 20.00, Currency.BOLIVIANOS, "Daenerys Targaryen",
                                              Department.BENI
        );

        when(accountService.create(any(Account.class)))
            .thenReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/accounts/")
                            .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());

        Mockito.verify(accountService).create(eq(accountExpected));
    }

    @Test
    public void createAccountWrongHolder() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO();
        accountInsertDTO.setBalance(20.00);
        accountInsertDTO.setDepartment(Department.BENI);
        accountInsertDTO.setCurrency(Currency.BOLIVIANOS);

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/accounts/")
                            .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void createAccountWrongBalance() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO();
        accountInsertDTO.setHolder("John Snow");
        accountInsertDTO.setBalance(-20.00);
        accountInsertDTO.setDepartment(Department.BENI);
        accountInsertDTO.setCurrency(Currency.BOLIVIANOS);

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/accounts/")
                            .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(accountService);
    }
}