package org.benja.services.parser;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.benja.model.Transaction;

import java.io.*;
import java.util.*;

public class JacksonCsvParser implements RecordParser {

    private static final CsvMapper MAPPER = new CsvMapper();
    private static final String FILE_ENCODING = "ISO-8859-1";

    @Override
    public List<Transaction> parseRecords(File file) {
        try (
                var fileStream = new FileInputStream(file);
                var reader = new InputStreamReader(fileStream, FILE_ENCODING)
        ) {
            return MAPPER
                    .readerFor(Transaction.class)
                    .with(CsvSchema.emptySchema().withHeader())
                    .<Transaction>readValues(reader)
                    .readAll();
        } catch (IOException e) {
            System.err.println("Error reading file " + file.getAbsolutePath());
            return Collections.emptyList();
        }
    }
}
