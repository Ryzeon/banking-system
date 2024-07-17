package me.ryzeon.bankingsystem.transaction.interfaces.res;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.transaction.domain.model.commands.CreateTransactionCommand;
import me.ryzeon.bankingsystem.transaction.domain.services.TransactionCommandService;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.CreateDepositTransactionResource;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.CreateWithdrawalTransactionResource;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.TransactionResource;
import me.ryzeon.bankingsystem.transaction.interfaces.res.transforms.CreateTransactionCommandFromResourceAssembler;
import me.ryzeon.bankingsystem.transaction.interfaces.res.transforms.TransactionResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TransactionController {

    private final TransactionCommandService transactionCommandService;

    public ResponseEntity<TransactionResource> createTransaction(CreateTransactionCommand command) {
        var transaction = transactionCommandService.handle(command);
        if (transaction.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var transactionResource = TransactionResourceFromEntityAssembler.toResourceFromEntity(transaction.get());
        return new ResponseEntity<>(transactionResource, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResource> createDepositTransaction(@RequestBody CreateDepositTransactionResource resource) {
        var command = CreateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);
        return createTransaction(command);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResource> createWithdrawTransaction(@RequestBody CreateWithdrawalTransactionResource resource) {
        var command = CreateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);
        return createTransaction(command);
    }
}
