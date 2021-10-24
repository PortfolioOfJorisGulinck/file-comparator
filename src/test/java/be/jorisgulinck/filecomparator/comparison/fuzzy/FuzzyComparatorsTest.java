package be.jorisgulinck.filecomparator.comparison.fuzzy;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FuzzyComparatorsTest {

    FuzzyComparatorFactory fuzzyComparatorFactory;
    Transaction transaction;
    List<Transaction> listOfTransactions;

    @BeforeEach
    void setup() {
        fuzzyComparatorFactory = new FuzzyComparatorFactory();
        transaction = new Transaction(
                "HKBGUB",
                "ID2",
                "profileName",
                "transactionDate",
                "transactionAmount",
                "transactionNarrative",
                "transactionDescription",
                "transactionType",
                "walletReference",
                "fileName",
                "0");
        listOfTransactions = new ArrayList<>(Arrays.asList(
                new Transaction(
                        "HKBGUB",
                        "ID2",
                        "profile Name",
                        "transactionDate",
                        "transaction Amount",
                        "transactionNarrative",
                        "transactionDescription",
                        "transactionType",
                        "walletReference",
                        "fileName",
                        "0"),
                new Transaction(
                        "ZZMMXX",
                        "ID 3 totally different data string",
                        "profile Name totally different data string",
                        "transactionDate totally different data string",
                        "transactionAmount totally different data string",
                        "transactionNarrative totally different data string",
                        "transactionDescription totally different data string",
                        "transactionType totally different data string",
                        "walletReference totally different data string",
                        "fileName",
                        "0")));
    }

    @Test
    void compareTransactionsFuzzyWithSimpleRatio() {

        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.createFuzzyComparator("Simple Ratio");
        List<Transaction> comparedList = fuzzyComparator.compareTransactionsFuzzy(transaction, listOfTransactions, 90);

        assertEquals(comparedList.get(0).getTransactionId(), transaction.getTransactionId());
    }

    @Test
    void compareTransactionsFuzzyWithPartialRatio() {

        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.createFuzzyComparator("Partial Ratio");
        List<Transaction> comparedList = fuzzyComparator.compareTransactionsFuzzy(transaction, listOfTransactions, 90);

        assertEquals(comparedList.get(0).getTransactionId(), transaction.getTransactionId());
    }

    @Test
    void compareTransactionsFuzzyWithTokenSortRatio() {

        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.createFuzzyComparator("Token Sort Ratio");
        List<Transaction> comparedList = fuzzyComparator.compareTransactionsFuzzy(transaction, listOfTransactions, 80);

        assertEquals(comparedList.get(0).getTransactionId(), transaction.getTransactionId());
    }

    @Test
    void compareTransactionsFuzzyWithTokenSortPartialRatio() {

        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.createFuzzyComparator("Token Sort Partial Ratio");
        List<Transaction> comparedList = fuzzyComparator.compareTransactionsFuzzy(transaction, listOfTransactions, 90);

        assertEquals(comparedList.get(0).getTransactionId(), transaction.getTransactionId());
    }

    @Test
    void compareTransactionsFuzzyWithTokenSetRatio() {

        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.createFuzzyComparator("Token Set Ratio");
        List<Transaction> comparedList = fuzzyComparator.compareTransactionsFuzzy(transaction, listOfTransactions, 80);

        assertEquals(comparedList.get(0).getTransactionId(), transaction.getTransactionId());
    }

    @Test
    void compareTransactionsFuzzyWithTokenSetPartialRatio() {

        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.createFuzzyComparator("Token Set Partial Ratio");
        List<Transaction> comparedList = fuzzyComparator.compareTransactionsFuzzy(transaction, listOfTransactions, 80);

        assertEquals(comparedList.get(0).getTransactionId(), transaction.getTransactionId());
    }

}
