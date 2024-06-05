package org.benja;

import org.benja.model.Transaction;
import org.benja.model.TransactionGroup;
import org.benja.services.TransactionGrouper;
import org.benja.services.parser.FileParser;
import org.benja.services.validator.EndBalanceValidator;
import org.benja.services.validator.NoValidationErrorsValidator;
import org.benja.services.validator.ReferenceNumberValidator;
import org.benja.services.validator.ValidatingFunction;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
//        return System.getProperty("user.dir");

        // This is the debugging code
        URL res = Main.class.getClassLoader().getResource("");
        if (res == null) {
            throw new Exception("Directory not found!");
        }
        return res.getPath();
    }

    private static List<Transaction> parseFiles(String dirPath) {
        FileParser fileParser = FileParser.getInstance();
        return fileParser.readFilesFromDirectory(dirPath);
    }

    private static void printTransactionDetails(List<Transaction> transactions) {
        TransactionGrouper grouper = new TransactionGrouper();
        final List<ValidatingFunction<TransactionGroup>> validators = new ArrayList<>(
                Arrays.asList(new ReferenceNumberValidator(), new EndBalanceValidator(), new NoValidationErrorsValidator())
        );
        // setup groups
        List<TransactionGroup> transactionGroups = grouper.groupTransactionsByAccount(transactions);
        int transactionsSize = transactions.isEmpty() ? 0 : transactions.size();
        List<Transaction> invalidReferenceTransactions = grouper.findTransactionsWithDuplicateReferences(transactions);
        int invalidReferenceTransactionsSize = invalidReferenceTransactions.isEmpty() ? 0 : invalidReferenceTransactions.size();

        // print out some information about the transactions
        System.out.println("A total of " + transactionsSize + " were parsed successfully");
        System.out.println("Of them, " + invalidReferenceTransactionsSize + " had invalid references");

        for (ValidatingFunction<TransactionGroup> validator : validators) {
            validator.validate(transactionGroups);
        }
    }
}