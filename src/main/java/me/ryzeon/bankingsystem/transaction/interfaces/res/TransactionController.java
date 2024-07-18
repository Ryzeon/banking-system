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

/**
 * Controller for handling transaction-related operations.
 * This controller provides endpoints for creating deposit and withdrawal transactions.
 * It uses {@link TransactionCommandService} to process the transactions.
 */
@RestController
@RequestMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TransactionController {

    private final TransactionCommandService transactionCommandService;

    /**
     * Creates a transaction based on the provided {@link CreateTransactionCommand}.
     * This method handles the transaction creation logic and returns the result as a {@link ResponseEntity}.
     *
     * @param command The command object containing the transaction details.
     * @return A {@link ResponseEntity} containing the created {@link TransactionResource} or a bad request status if the transaction fails.
     */
    public ResponseEntity<TransactionResource> createTransaction(CreateTransactionCommand command) {
        var transaction = transactionCommandService.handle(command);
        if (transaction.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var transactionResource = TransactionResourceFromEntityAssembler.toResourceFromEntity(transaction.get());
        return new ResponseEntity<>(transactionResource, HttpStatus.OK);
    }

    /**
     * Endpoint for creating a deposit transaction.
     * Accepts a {@link CreateDepositTransactionResource} object as the request body.
     *
     * @param resource The resource object containing the deposit transaction details.
     * @return A {@link ResponseEntity} with the created transaction resource or a bad request status.
     */
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResource> createDepositTransaction(@RequestBody CreateDepositTransactionResource resource) {
        var command = CreateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);
        return createTransaction(command);
    }

    /**
     * Endpoint for creating a withdrawal transaction.
     * Accepts a {@link CreateWithdrawalTransactionResource} object as the request body.
     *
     * @param resource The resource object containing the withdrawal transaction details.
     * @return A {@link ResponseEntity} with the created transaction resource or a bad request status.
     */
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResource> createWithdrawTransaction(@RequestBody CreateWithdrawalTransactionResource resource) {
        var command = CreateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);
        return createTransaction(command);
    }
}