package be.jorisgulinck.filecomparator.dto;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps a model Transaction object to a dto object
 */
@Component
public class DtoMapper {

    public ComparisonResultDto createComparisonResult(List<Transaction> originalList, List<Transaction> comparedResult) {
        ComparisonResultDto comparisonResultDto = new ComparisonResultDto();
        comparisonResultDto.setTotalRecords(originalList.size());
        comparisonResultDto.setMatchingRecords(originalList.size() - comparedResult.size());
        comparisonResultDto.setUnmatchedRecords(comparedResult.size());
        return comparisonResultDto;
    }

    private TransactionDto createUnmatchedTransactionResult(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setAmount(transaction.getTransactionAmount());
        transactionDto.setDate(transaction.getTransactionDate());
        transactionDto.setReference(transaction.getWalletReference());

        return transactionDto;
    }

    public List<TransactionDto> createListUnmatchedTransactionResult(List<Transaction> transactions) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.forEach(transaction -> transactionDtos.add(createUnmatchedTransactionResult(transaction)));
        return transactionDtos;
    }
}
