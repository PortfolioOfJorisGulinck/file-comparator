package be.jorisgulinck.filecomparator.mappers;

import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoMapperTest {

    private DtoMapper mapper;

    @BeforeEach
    void setUp() {
       mapper = new DtoMapper();
    }

    @Test
    void createComparisonResult() {
        List<Transaction> originalList = new ArrayList<>(Arrays.asList(new Transaction("ID1"), new Transaction("ID1"), new Transaction("ID2")));
        List<Transaction> comparedResult = new ArrayList<>(Arrays.asList(new Transaction("ID1"), new Transaction("ID3")));

        ComparisonResultDto comparisonResultDto = mapper.createComparisonResult(originalList, comparedResult);
        assertEquals(comparisonResultDto.getNumberOfDuplicates(), 1);
        assertEquals(comparisonResultDto.getTotalRecords(), 3);
    }

    @Test
    void createListUnmatchedTransactionResult() {
        List<Transaction> transactions = new ArrayList<>(Arrays.asList(new Transaction("ID1"), new Transaction("ID1"), new Transaction("ID2")));

        List<TransactionDto> transactionDtos = mapper.createListUnmatchedTransactionResult(transactions);
        assertEquals(transactions.get(0).getTransactionId(), transactionDtos.get(0).getTransactionId());
        assertEquals(transactions.get(1).getTransactionId(), transactionDtos.get(1).getTransactionId());
    }

    @Test
    void transactionDtoToTransaction() {
        TransactionDto transactionDto = new TransactionDto("transactionId", "profileName",
                "transactionDate", "transactionAmount", "transactionNarrative",
                "transactionDescription", "transactionType", "walletReference",
                "fileName");

        Transaction transaction = mapper.transactionDtoToTransaction(transactionDto);
        assertEquals(transactionDto.getTransactionId(), transaction.getTransactionId());
        assertEquals(transactionDto.getTransactionDate(), transaction.getTransactionDate());
        assertEquals(transactionDto.getTransactionAmount(), transaction.getTransactionAmount());
    }
}


