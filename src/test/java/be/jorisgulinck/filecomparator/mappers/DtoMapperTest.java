package be.jorisgulinck.filecomparator.mappers;

import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoMapperTest {

    private DtoMapper mapper;
    private List<Transaction> originalList;
    List<Transaction> comparedResult;

    @BeforeEach
    void setUp() {
        mapper = new DtoMapper();
        originalList = new ArrayList<>(Arrays.asList(new Transaction("ID1"),
                new Transaction("ID1"), new Transaction("ID2")));
        comparedResult = new ArrayList<>(Arrays.asList(new Transaction("ID1"),
                new Transaction("ID3")));
    }

    @Test
    void createListOfUniqueTransactionDtos() {
        List<TransactionDto> dtoList = mapper.createListOfTransactionDtos(originalList);
        assertEquals(3, dtoList.size());

    }

    @Test
    void createListOfTransactionDtos() {
        List<TransactionDto> transactionDtos = mapper.createListOfTransactionDtos(originalList);
        assertEquals(originalList.get(0).getTransactionId(), transactionDtos.get(0).getTransactionId());
        assertEquals(originalList.get(1).getTransactionId(), transactionDtos.get(1).getTransactionId());
    }

    @Test
    void transactionDtoToTransaction() {
        TransactionDto transactionDto = new TransactionDto("JKHKJHFSFZ", "transactionId", "profileName",
                "transactionDate", "transactionAmount", "transactionNarrative",
                "transactionDescription", "transactionType", "walletReference",
                "fileName", null);

        Transaction transaction = mapper.transactionDtoToTransaction(transactionDto);
        assertEquals(transactionDto.getTransactionId(), transaction.getTransactionId());
        assertEquals(transactionDto.getTransactionDate(), transaction.getTransactionDate());
        assertEquals(transactionDto.getTransactionAmount(), transaction.getTransactionAmount());
    }

}