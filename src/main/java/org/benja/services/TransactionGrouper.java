package org.benja.services;

import org.benja.model.Transaction;
import org.benja.model.TransactionGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionGrouper {
    public List<Transaction> findTransactionsWithDuplicateReferences(List<Transaction> transactions) {
        Set<String> uniqueReferences = new HashSet<>();
        List<Transaction> invalidReferenceTransactions = new ArrayList<>();

        // Check each transaction
        for (Transaction transaction : transactions) {
            String reference = transaction.reference();
            // If reference is null or not unique, add to invalidReferenceTransactions
            // Because it's a Set, it won't add a value that is already there, else it would be a List
            if (reference == null || !uniqueReferences.add(reference)) {
                invalidReferenceTransactions.add(transaction);
            }
        }

        return invalidReferenceTransactions;
    }

    public List<TransactionGroup> groupTransactionsByAccount(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::accountNumber))
                .entrySet().stream()
                .map(entry -> new TransactionGroup(
                        entry.getKey(),
                        entry.getValue(),
                        findTransactionsWithDuplicateReferences(entry.getValue()),
                        findTransactionsWithInvalidEndBalance(entry.getValue()))
                )
                .collect(Collectors.toList());
    }

    public BigDecimal calculateEndBalance(Transaction transaction) {
        return transaction.startBalance().add(transaction.mutation()).setScale(2, RoundingMode.HALF_UP);
    }

    private List<Transaction> findTransactionsWithInvalidEndBalance(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.endBalance().compareTo(calculateEndBalance(transaction)) != 0)
                .collect(Collectors.toList());
    }
}
