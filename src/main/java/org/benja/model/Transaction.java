package org.benja.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;

public record Transaction(
        @JsonProperty("Reference")
        @JacksonXmlProperty(localName = "reference")
        String reference,

        @JsonProperty("Account Number")
        @JacksonXmlProperty(localName = "accountNumber")
        String accountNumber,

        @JsonProperty("Description")
        @JacksonXmlProperty(localName = "description")
        String description,

        @JsonProperty("Start Balance")
        @JacksonXmlProperty(localName = "startBalance")
        BigDecimal startBalance,

        @JsonProperty("Mutation")
        @JacksonXmlProperty(localName = "mutation")
        BigDecimal mutation,

        @JsonProperty("End Balance")
        @JacksonXmlProperty(localName = "endBalance")
        BigDecimal endBalance
) { }
