package me.ryzeon.bankingsystem.transaction.interfaces.res.transforms;

import me.ryzeon.bankingsystem.transaction.domain.model.aggregates.Transaction;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.TransactionResource;

public class TransactionResourceFromEntityAssembler {

    private TransactionResourceFromEntityAssembler() {
    }

    /**
     * Converts a {@link Transaction} entity to a {@link TransactionResource} DTO.
     * This static method maps the fields from a Transaction entity to a new TransactionResource object,
     * facilitating the transformation of data for API responses.
     *
     * @param transaction The transaction entity to be transformed.
     * @return A new {@link TransactionResource} instance populated with data from the provided transaction entity.
     */
    public static TransactionResource toResourceFromEntity(Transaction transaction) {
        return new TransactionResource(
                transaction.getId(),
                transaction.getAccountNumber(),
                transaction.getOldBalance(),
                transaction.getAmount()
        );
    }
}
