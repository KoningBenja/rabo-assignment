package org.benja;

import org.benja.model.Transaction;
import org.benja.services.TransactionReporter;
import org.benja.services.parser.FileParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            TransactionReporter transactionReporter = new TransactionReporter();
            FileParser fileParser = FileParser.getInstance();

            List<Transaction> transactions = fileParser.parseFiles();
            transactionReporter.reportTransactionDetails(transactions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}