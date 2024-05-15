# Interview Assignment

## Description

This is a Maven CLI Application written in Java 21 that processes '.csv' and '.xml' files in the directory where the application is executed. The purpose of the application is to parse these files in search of transaction information and print out details about these transactions.

## Prerequisites

Please ensure that you have Java version 21 set up on your system.

## Usage

To run this application, follow these steps:

1. Navigate to the directory where the jar file resides.
2. Run the jar file.

No arguments are required, the application will check all files in the directory from which you are running the jar.

### Debugging

In the Main class you'll find a method called getDirectory that throws an Exception. To quickly debug the code, do the following:

1. Comment out the current code
2. Uncomment the section of code underneath the comment that reads: "This is the debugging code"

When you now run the application, it will make use of the main/resources folder to get the files. In there you'll find a csv and xml file with some transactions in it.

## Design

The main data container model in this project is called Transactions and is located in the org.benja.model package. In there you'll also find a second model called TransactionGroup. This model is used for grouping the transactions by account number and grouping some of the transactions that are invalid in some way.

In the org.benja.services package you'll find the services used in this application. There is a subfolder called parser that contains the parsing service logic.
This was set up in such a way that more parsers can be added in the future. The FileParser is set up as a singleton.

The TransactionGrouper is used to group the transactions in various ways.