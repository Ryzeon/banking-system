package me.ryzeon.bankingsystem.transaction.interfaces.res.transforms;

import me.ryzeon.bankingsystem.transaction.domain.model.aggregates.Transaction;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.TransactionResource;

public class TransactionResourceFromEntityAssembler {

    private TransactionResourceFromEntityAssembler() {
    }

    public static TransactionResource toResourceFromEntity(Transaction transaction) {
        return new TransactionResource(
                transaction.getId(),
                transaction.getAccountNumber(),
                transaction.getOldBalance(),
                transaction.getAmount()
        );
    }
}
