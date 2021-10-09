package be.jorisgulinck.filecomparator.mappers;

import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.models.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mapper class which helps with te mapping of {@link Transaction} and {@link TransactionDto} objects.
 */
@Component
public class DtoMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Maps a {@link Transaction} object to a {@link TransactionDto} object.
     *
     * @param transaction {@link Transaction}.
     * @return {@link TransactionDto}.
     */
    private TransactionDto transactionToTransactionDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

    /**
     * Maps a {@link TransactionDto} object to a {@link Transaction} object.
     *
     * @param transactionDto {@link TransactionDto}.
     * @return {@link Transaction}.
     */
    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }

    /**
     * Removes duplicates in a collection of {@link Transaction} objects.
     *
     * @param transactions Collection of {@link Transaction} objects.
     * @return Collection of unique {@link Transaction} objects.
     */
    public List<Transaction> createListOfUniqueTransactions(List<Transaction> transactions) {
        Set<Transaction> uniqueTransactionSet = new HashSet<>();
        transactions.forEach(transaction -> uniqueTransactionSet.add(transaction));
        List<Transaction> uniqueTransactionList = new ArrayList<>();
        uniqueTransactionSet.forEach(transaction -> uniqueTransactionList.add(transaction));
        return uniqueTransactionList;
    }

    /**
     * Maps a collection of {@link TransactionDto} objects to a collection of {@link Transaction} objects.
     *
     * @param transactionsDto Collection of {@link TransactionDto} objects.
     * @return Collection of {@link Transaction} objects.
     */
    public List<Transaction> createListOfTransactions(List<TransactionDto> transactionsDto) {
        List<Transaction> transactions = new ArrayList<>();
        transactionsDto.forEach(transactionDto -> transactions.add(transactionDtoToTransaction(transactionDto)));
        return transactions;
    }

    /**
     * Maps a collection of {@link Transaction} objects to a collection of {@link TransactionDto} objects.
     *
     * @param transactions Collection of {@link Transaction} objects.
     * @return Collection of {@link TransactionDto} objects.
     */
    public List<TransactionDto> createListOfTransactionDtos(List<Transaction> transactions) {
        List<TransactionDto> uniqueTransactionDtos = new ArrayList<>();
        transactions.forEach(transaction -> uniqueTransactionDtos.add(transactionToTransactionDto(transaction)));
        return uniqueTransactionDtos;
    }

}
