package me.ryzeon.bankingsystem.transaction.infrastructure.persistence.jpa.repositories;

import me.ryzeon.bankingsystem.transaction.domain.model.aggregates.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
