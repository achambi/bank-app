package bo.com.mondongo.bankapp.controller;

import bo.com.mondongo.bankapp.dto.AccountInsertDTO;
import bo.com.mondongo.bankapp.entity.Account;
import bo.com.mondongo.bankapp.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@Api(value = "account module", description = "Account Operations")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(@Qualifier("AccountService") AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Create a account", response = ResponseEntity.class)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity createAccount(@RequestBody @Valid AccountInsertDTO accountDto) {
        return accountService.create(new Account(accountDto));
    }
}
