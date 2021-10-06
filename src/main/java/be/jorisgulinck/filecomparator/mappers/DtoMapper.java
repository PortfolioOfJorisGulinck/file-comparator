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

    private TransactionDto createUnmatchedTransactionResult(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

    public List<TransactionDto> createListUnmatchedTransactionResult(List<Transaction> transactions) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.forEach(transaction -> transactionDtos.add(createUnmatchedTransactionResult(transaction)));
        return transactionDtos;
    }

    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }
}
