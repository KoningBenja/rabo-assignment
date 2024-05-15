import org.benja.model.Transaction;
import org.benja.model.TransactionGroup;
import org.benja.services.TransactionGrouper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionGrouperTest {
    @Test
    public void testFindTransactionsWithDuplicateReferencesReturnsDuplicates() {
        TransactionGrouper transactionGrouper = new TransactionGrouper();

        Transaction transaction1 = new Transaction(
                "ref1",
                "account1",
                "na",
                new BigDecimal("200"),
                new BigDecimal("-10"),
                new BigDecimal("190")
        );
        Transaction transaction2 = new Transaction(
                "ref1",
                "account2",
                "na",
                new BigDecimal("200"),
                new BigDecimal("10"),
                new BigDecimal("210")
        );
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        List<Transaction> duplicates = transactionGrouper.findTransactionsWithDuplicateReferences(transactions);

        assertEquals(1, duplicates.size());
        assertEquals(transaction2, duplicates.getFirst());
    }

    @Test
    public void testGroupTransactionsByAccountGroupsCorrectly() {
        TransactionGrouper transactionGrouper = new TransactionGrouper();

        Transaction transaction1 = new Transaction(
                "ref1",
                "account1",
                "na",
                new BigDecimal("200"),
                new BigDecimal("-10"),
                new BigDecimal("190")
        );
        Transaction transaction2 = new Transaction(
                "ref2",
                "account1",
                "na",
                new BigDecimal("200"),
                new BigDecimal("10"),
                new BigDecimal("210")
        );
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        List<TransactionGroup> transactionGroups = transactionGrouper.groupTransactionsByAccount(transactions);

        assertEquals(1, transactionGroups.size());
        assertEquals(2, transactionGroups.getFirst().allTransactions().size());
    }
}