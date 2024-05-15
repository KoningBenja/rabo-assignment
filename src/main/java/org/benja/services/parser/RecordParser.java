package org.benja.services.parser;

import org.benja.model.Transaction;

import java.io.File;
import java.util.List;

public interface RecordParser {
    List<Transaction> parseRecords(File file);
}
