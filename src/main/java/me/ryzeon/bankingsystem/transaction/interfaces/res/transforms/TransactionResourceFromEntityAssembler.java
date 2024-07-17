package me.ryzeon.bankingsystem.transaction.interfaces.res.transforms;

import lombok.experimental.UtilityClass;
import me.ryzeon.bankingsystem.transaction.domain.model.aggregates.Transaction;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.TransactionResource;

@UtilityClass
public class TransactionResourceFromEntityAssembler {

    public TransactionResource toResourceFromEntity(Transaction transaction) {
        return new TransactionResource(
                transaction.getId(),
                transaction.getAccountNumber(),
                transaction.getOldBalance(),
                transaction.getAmount()
        );
    }
}
