package me.ryzeon.bankingsystem.account.interfaces.res;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.account.domain.model.commands.CloseAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountDetailsCommand;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountHolderByAccountNumberQuery;
import me.ryzeon.bankingsystem.account.domain.services.AccountCommandService;
import me.ryzeon.bankingsystem.account.domain.services.AccountQueryService;
import me.ryzeon.bankingsystem.account.interfaces.res.resources.AccountResource;
import me.ryzeon.bankingsystem.account.interfaces.res.resources.CreateAccountResource;
import me.ryzeon.bankingsystem.account.interfaces.res.resources.UpdateAccountDetailsResource;
import me.ryzeon.bankingsystem.account.interfaces.res.transforms.AccountResourceFromEntityAssembler;
import me.ryzeon.bankingsystem.account.interfaces.res.transforms.CreateAccountCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountController {

    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;

    @PostMapping
    public ResponseEntity<AccountResource> createAccount(@RequestBody CreateAccountResource resource) {
        var createAccountCommand = CreateAccountCommandFromResourceAssembler.toCommandFromResource(resource);
        var account = accountCommandService.handle(createAccountCommand);
        if (account.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var accountResource = AccountResourceFromEntityAssembler.toResourceFromEntity(account.get());
        return new ResponseEntity<>(accountResource, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResource> getAccount(@PathVariable String accountNumber) {
        var query = new GetAccountHolderByAccountNumberQuery(accountNumber);
        var account = accountQueryService.handle(query);
        if (account.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var accountResource = AccountResourceFromEntityAssembler.toResourceFromEntity(account.get());
        return ResponseEntity.ok(accountResource);
    }

    @PutMapping("details/{accountNumber}")
    public ResponseEntity<Void> updateAccountDetails(@PathVariable String accountNumber, @RequestBody UpdateAccountDetailsResource resource) {
        var command = new UpdateAccountDetailsCommand(accountNumber, resource.names(), resource.lastNames(), resource.email(), resource.documentNumber());
        accountCommandService.handle(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("close/{accountNumber}")
    public ResponseEntity<Void> closeAccount(@PathVariable String accountNumber) {
        accountCommandService.handle(new CloseAccountCommand(accountNumber));
        return ResponseEntity.ok().build();
    }
}
