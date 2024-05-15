package org.benja;

import org.benja.model.Transaction;
import org.benja.model.TransactionGroup;
import org.benja.services.TransactionGrouper;
import org.benja.services.parser.FileParser;

import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String dirPath = getDirectory();
            List<Transaction> transactions = parseFiles(dirPath);

            printTransactionDetails(transactions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getDirectory() throws Exception {
        return System.getProperty("user.dir");

        // This is the debugging code
//        URL res = Main.class.getClassLoader().getResource("");
//        if (res == null) {
//            throw new Exception("Directory not found!");
//        }
//        return res.getPath();
    }

    private static List<Transaction> parseFiles(String dirPath) {
        FileParser fileParser = FileParser.getInstance();
        return fileParser.readFilesFromDirectory(dirPath);
    }

    private static void printTransactionDetails(List<Transaction> transactions) {
        TransactionGrouper grouper = new TransactionGrouper();

        // setup groups
        List<TransactionGroup> transactionGroups = grouper.groupTransactionsByAccount(transactions);
        int transactionsSize = transactions.isEmpty() ? 0 : transactions.size();
        List<Transaction> invalidReferenceTransactions = grouper.findTransactionsWithDuplicateReferences(transactions);
        int invalidReferenceTransactionsSize = invalidReferenceTransactions.isEmpty() ? 0 : invalidReferenceTransactions.size();

        // print out some information about the transactions
        System.out.println("A total of " + transactionsSize + " were parsed successfully");
        System.out.println("Of them, " + invalidReferenceTransactionsSize + " had invalid references");

        for (TransactionGroup aTransactionGroup : transactionGroups) {
            System.out.println("\nTransaction Group for IBAN: " + aTransactionGroup.accountNumber());
            System.out.println("Contains " + aTransactionGroup.allTransactions().size() + " transaction(s)");

            if (!aTransactionGroup.invalidReferenceTransactions().isEmpty()) {
                System.out.println("The following transactions have invalid reference numbers:");
                aTransactionGroup.invalidReferenceTransactions().forEach(invalidReferenceNumber -> {
                    System.out.println("Reference number: " + invalidReferenceNumber.reference());
                    System.out.println("Description: " + invalidReferenceNumber.description());
                });
            } else if (!aTransactionGroup.invalidEndBalanceTransactions().isEmpty()){
                System.out.println("The following transactions have invalid end balances:");
                aTransactionGroup.invalidEndBalanceTransactions().forEach(invalidEndBalance -> {
                    System.out.println("Reference number: " + invalidEndBalance.reference());
                    System.out.println("Current end balance: " + invalidEndBalance.endBalance());
                    System.out.println("Expected end balance: " + grouper.calculateEndBalance(invalidEndBalance));
                });
            } else {
                System.out.println("No validation errors for these transaction(s).");
            }
        }
    }
}