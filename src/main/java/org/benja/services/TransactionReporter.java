package org.benja.services;

import org.benja.model.Transaction;
import org.benja.model.TransactionGroup;
import org.benja.services.validator.EndBalanceValidator;
import org.benja.services.validator.NoValidationErrorsValidator;
import org.benja.services.validator.ReferenceNumberValidator;
import org.benja.services.validator.ValidatingFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionReporter {

    private final List<ValidatingFunction<TransactionGroup>> validators;

    public TransactionReporter() {
        this.validators = new ArrayList<>(
                Arrays.asList(
                        new ReferenceNumberValidator(),
                        new EndBalanceValidator(),
                        new NoValidationErrorsValidator()
                )
        );
    }

    public void reportTransactionDetails(List<Transaction> transactions) {
        TransactionGrouper grouper = new TransactionGrouper();

        // setup groups
        List<TransactionGroup> transactionGroups = grouper.groupTransactionsByAccount(transactions);
        int transactionsSize = transactions.isEmpty() ? 0 : transactions.size();
        List<Transaction> invalidReferenceTransactions = grouper.findTransactionsWithDuplicateReferences(transactions);
        int invalidReferenceTransactionsSize = invalidReferenceTransactions.isEmpty() ? 0 : invalidReferenceTransactions.size();

        // print out some information about the transactions
        System.out.println("A total of " + transactionsSize + " were parsed successfully");
        System.out.println("Of them, " + invalidReferenceTransactionsSize + " had invalid references");

        validators.forEach(validator -> validator.validate(transactionGroups));
    }
}
