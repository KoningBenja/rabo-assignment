package org.benja.services.validator;

import org.benja.model.TransactionGroup;
import org.benja.services.TransactionGrouper;

import java.util.List;

public class EndBalanceValidator implements ValidatingFunction<TransactionGroup> {


    @Override
    public boolean couldBeApplied(TransactionGroup transactionGroup) {
        return !transactionGroup.invalidEndBalanceTransactions().isEmpty();
    }

    @Override
    public void validate(List<TransactionGroup> transactionGroups) {
        final TransactionGrouper grouper = new TransactionGrouper();

        for (TransactionGroup aTransactionGroup : transactionGroups) {
            if (couldBeApplied(aTransactionGroup)) {
                System.out.println("The following transactions have invalid end balances:");
                aTransactionGroup.invalidEndBalanceTransactions().forEach(invalidEndBalance -> {
                            System.out.println("Reference number: " + invalidEndBalance.reference());
                            System.out.println("Current end balance: " + invalidEndBalance.endBalance());
                            System.out.println("Expected end balance: " + grouper.calculateEndBalance(invalidEndBalance));
                        }
                );
            }

        }
    }
}
