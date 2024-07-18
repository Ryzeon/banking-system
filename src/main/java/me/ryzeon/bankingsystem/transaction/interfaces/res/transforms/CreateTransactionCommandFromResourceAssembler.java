package me.ryzeon.bankingsystem.transaction.interfaces.res.transforms;

import me.ryzeon.bankingsystem.transaction.domain.model.commands.CreateTransactionCommand;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.CreateDepositTransactionResource;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.CreateWithdrawalTransactionResource;

public class CreateTransactionCommandFromResourceAssembler {

    private CreateTransactionCommandFromResourceAssembler() {
    }

    /**
     * Overloaded method to convert a CreateWithdrawalTransactionResource to a CreateTransactionCommand
     *
     * @param resource CreateWithdrawalTransactionResource
     * @return CreateTransactionCommand
     */
    public static CreateTransactionCommand toCommandFromResource(CreateWithdrawalTransactionResource resource) {
        return new CreateTransactionCommand(
                resource.accountNumber(),
                resource.amount(),
                "WITHDRAWAL"
        );
    }

    /**
     * Overloaded method to convert a CreateDepositTransactionResource to a CreateTransactionCommand
     *
     * @param resource CreateDepositTransactionResource
     * @return CreateTransactionCommand
     */
    public static CreateTransactionCommand toCommandFromResource(CreateDepositTransactionResource resource) {
        return new CreateTransactionCommand(
                resource.accountNumber(),
                resource.amount(),
                "DEPOSIT"
        );
    }
}
