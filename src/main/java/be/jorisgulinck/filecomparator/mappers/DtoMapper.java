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
 * Maps a model Transaction object to a dto object
 */
@Component
public class DtoMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    private TransactionDto transactionToTransactionDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

    public List<Transaction> createListOfUniqueTransactions(List<Transaction> transactions) {
        Set<Transaction> uniqueTransactionSet = new HashSet<>();
        transactions.forEach(transaction -> uniqueTransactionSet.add(transaction));
        List<Transaction> uniqueTransactionList = new ArrayList<>();
        uniqueTransactionSet.forEach(transaction -> uniqueTransactionList.add(transaction));
        return uniqueTransactionList;
    }

    public List<Transaction> createListOfTransactions(List<TransactionDto> transactionsDto) {
        List<Transaction> transactions = new ArrayList<>();
        transactionsDto.forEach(transactionDto -> transactions.add(transactionDtoToTransaction(transactionDto)));
        return transactions;
    }

    public List<TransactionDto> createListOfTransactionDtos(List<Transaction> transactions) {
        List<TransactionDto> uniqueTransactionDtos = new ArrayList<>();
        transactions.forEach(transaction -> uniqueTransactionDtos.add(transactionToTransactionDto(transaction)));
        return uniqueTransactionDtos;
    }

    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }
}
