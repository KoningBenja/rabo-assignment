package org.benja.services.parser;

import org.benja.model.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileParser {
    private static final RecordParser csvRecordParser = new JacksonCsvParser();
    private static final RecordParser xmlRecordParser = new JacksonXmlParser();

    // Singleton pattern
    private static final FileParser INSTANCE = new FileParser();
    private FileParser() {}
    public static FileParser getInstance() {
        return INSTANCE;
    }

    public List<Transaction> readFilesFromDirectory(String directoryPath) {
        File folder = new File(directoryPath);
        File[] files = folder.listFiles();
        List<Transaction> transactions = new ArrayList<>();
        if (files != null) {
            Arrays.stream(files).forEach(file -> transactions.addAll(parseFile(file)));
        }
        return transactions;
    }

    private List<Transaction> parseFile(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".csv")) {
            return csvRecordParser.parseRecords(file);
        } else if (fileName.endsWith(".xml")) {
            return xmlRecordParser.parseRecords(file);
        } else {
            System.err.println("Unsupported file type: " + fileName);
            return List.of(); // return empty list in case of an error
        }
    }
}
