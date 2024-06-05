package org.benja.services.validator;

import org.benja.model.TransactionGroup;

import java.util.List;

public class ReferenceNumberValidator implements ValidatingFunction<TransactionGroup>{

    @Override
    public boolean couldBeApplied(TransactionGroup transactionGroup) {
       return !transactionGroup.invalidReferenceTransactions().isEmpty();
    }

    @Override
    public void validate(List<TransactionGroup> transactionGroups) {
        for (TransactionGroup aTransactionGroup : transactionGroups) {
            System.out.println("\nTransaction Group for IBAN: " + aTransactionGroup.accountNumber());
            System.out.println("Contains " + aTransactionGroup.allTransactions().size() + " transaction(s)");

            if (couldBeApplied(aTransactionGroup)) {
                System.out.println("The following transactions have invalid reference numbers:");
                aTransactionGroup.invalidReferenceTransactions().forEach(invalidReferenceNumber -> {
                    System.out.println("Reference number: " + invalidReferenceNumber.reference());
                    System.out.println("Description: " + invalidReferenceNumber.description());
                });
            }

    }
    }
}
