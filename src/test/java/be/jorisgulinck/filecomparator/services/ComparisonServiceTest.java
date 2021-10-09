package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonServiceTest {

    private ComparisonService comparisonService;
    private List<Transaction> listOfTransactions1;
    private List<Transaction> listOfTransactions2;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        comparisonService = new ComparisonService();
        transaction = new Transaction(
                "ID2",
                "profileName2",
                "transactionDate2",
                "transactionAmount2",
                "transactionNarrative2",
                "transactionDescription2",
                "transactionType2",
                "walletReference2",
                "fileName",
                "0");
        listOfTransactions1 = new ArrayList<>(Arrays.asList(
                new Transaction("ID2",
                        "profileName2",
                        "transactionDate2",
                        "transactionAmount2",
                        "transactionNarrative2",
                        "transactionDescription2",
                        "transactionType2",
                        "walletReference2",
                        "fileName",
                        "0"),
                new Transaction("ID3",
                        "profileName3",
                        "transactionDate3",
                        "transactionAmount3",
                        "transactionNarrative3",
                        "transactionDescription3",
                        "transactionType3",
                        "walletReference3",
                        "fileName",
                        "0")));
        listOfTransactions2 = new ArrayList<>(Arrays.asList(
                new Transaction("ID2",
                        "profileName2",
                        "transactionDate2",
                        "transactionAmount2",
                        "transactionNarrative2",
                        "transactionDescription2",
                        "transactionType2",
                        "walletReference2",
                        "fileName",
                        "0"),
                new Transaction("ID 3",
                        "profileName3",
                        "transactionDate3",
                        "transaction Amount3",
                        "transactionNarrative3",
                        "transaction Description3",
                        "transactionType3",
                        "walletReference3",
                        "fileName",
                        "0")));
    }

    @Test
    void compareStrict() {
        List<Transaction> comparedList = comparisonService.compareStrict(listOfTransactions1, listOfTransactions2);

        assertEquals(comparedList.get(0).getTransactionId(), "ID3");
    }

    // TODO CHANGE TO SET
    /*
    @Test
    void compareFuzzy() {
        List<Transaction> comparedList = comparisonService.compareFuzzy(transaction, listOfTransactions2, "Simple Ratio", 80);

        assertEquals(comparedList.get(0).getWalletReference(), comparedList.get(0).getWalletReference());
    }
    */

}