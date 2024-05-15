package org.benja.services.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.benja.model.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JacksonXmlParser implements RecordParser {

    private static final XmlMapper MAPPER = new XmlMapper();

    @Override
    public List<Transaction> parseRecords(File file) {
        try {
            return MAPPER.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            System.err.println("Error reading file " + file.getAbsolutePath());
            return Collections.emptyList();
        }
    }
}
