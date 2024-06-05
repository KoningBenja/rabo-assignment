package org.benja.services.validator;

import org.benja.model.TransactionGroup;

import java.util.List;

public class NoValidationErrorsValidator implements ValidatingFunction<TransactionGroup> {

    @Override
    public boolean couldBeApplied(TransactionGroup transactionGroup) {
        return transactionGroup.invalidReferenceTransactions().isEmpty() && transactionGroup.invalidEndBalanceTransactions().isEmpty();
    }

    @Override
    public void validate(List<TransactionGroup> transactionGroups) {
        for (TransactionGroup aTransactionGroup : transactionGroups) {
            if (couldBeApplied(aTransactionGroup)) {
                System.out.println("No validation errors for these transaction(s).");
            }
        }
    }
}
