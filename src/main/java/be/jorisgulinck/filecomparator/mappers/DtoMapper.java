package be.jorisgulinck.filecomparator.mappers;

import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
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

    public ComparisonResultDto createComparisonResult(List<Transaction> originalList, List<Transaction> comparedResult) {
        ComparisonResultDto comparisonResultDto = new ComparisonResultDto();
        comparisonResultDto.setTotalRecords(originalList.size());
        comparisonResultDto.setMatchingRecords(originalList.size() - comparedResult.size());
        comparisonResultDto.setUnmatchedRecords(comparedResult.size());

        Set<Transaction> uniqueListOfTransactions = new HashSet<>(originalList);

        comparisonResultDto.setNumberOfDuplicates(originalList.size() - uniqueListOfTransactions.size());

        return comparisonResultDto;
    }

    private TransactionDto transactionToTransactionDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

    public Set<TransactionDto> createListOfUniqueTransactionDtos(List<Transaction> transactions) {
        Set<TransactionDto> uniqueTransactionDtos = new HashSet<>();
        transactions.forEach(transaction -> uniqueTransactionDtos.add(transactionToTransactionDto(transaction)));
        return uniqueTransactionDtos;
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
