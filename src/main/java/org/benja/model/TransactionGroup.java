package org.benja.model;

import java.util.List;

public record TransactionGroup(
        String accountNumber,
        List<Transaction> allTransactions,
        List<Transaction> invalidReferenceTransactions,
        List<Transaction> invalidEndBalanceTransactions
) { }
