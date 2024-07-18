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

/**
 * Controller for account-related operations.
 * <p>This controller handles the web requests for creating accounts, retrieving account details,
 * updating account details, and closing accounts. It interacts with the domain services to execute these operations.</p>
 */
@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountController {

    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;

    /**
     * Creates a new account based on the provided account resource.
     * <p>This method takes a {@link CreateAccountResource}, converts it to a command, and passes it to the
     * account command service for processing. If the account is successfully created, it returns the account resource
     * with a 201 status code. Otherwise, it returns a bad request response.</p>
     *
     * @param resource The account creation resource from the request body.
     * @return A {@link ResponseEntity} containing the created account resource or a bad request error.
     */
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

    /**
     * Retrieves the account details for a given account number.
     * <p>This method looks up an account by its number. If found, it returns the account details as a resource.
     * If the account is not found, it returns a 404 not found response.</p>
     *
     * @param accountNumber The account number path variable.
     * @return A {@link ResponseEntity} containing the account resource or a not found error.
     */
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

    /**
     * Updates the details of an existing account identified by the account number.
     * <p>This method accepts an account number and an {@link UpdateAccountDetailsResource} containing the new details.
     * It creates a command to update the account details and passes it to the account command service.</p>
     *
     * @param accountNumber The account number path variable.
     * @param resource      The resource containing the new account details from the request body.
     * @return A {@link ResponseEntity} indicating the result of the update operation.
     */
    @PutMapping("details/{accountNumber}")
    public ResponseEntity<Void> updateAccountDetails(@PathVariable String accountNumber, @RequestBody UpdateAccountDetailsResource resource) {
        var command = new UpdateAccountDetailsCommand(accountNumber, resource.names(), resource.lastNames(), resource.email(), resource.documentNumber());
        accountCommandService.handle(command);
        return ResponseEntity.ok().build();
    }

    /**
     * Closes an account identified by the account number.
     * <p>This method takes an account number, creates a {@link CloseAccountCommand}, and passes it to the account command
     * service to close the account.</p>
     *
     * @param accountNumber The account number path variable.
     * @return A {@link ResponseEntity} indicating the result of the close account operation.
     */
    @PostMapping("close/{accountNumber}")
    public ResponseEntity<Void> closeAccount(@PathVariable String accountNumber) {
        accountCommandService.handle(new CloseAccountCommand(accountNumber));
        return ResponseEntity.ok().build();
    }
}
