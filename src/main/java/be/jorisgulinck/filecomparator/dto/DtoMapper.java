package be.jorisgulinck.filecomparator.dto;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DtoMapper {

    public ComparisonResultDto createComparisonResult(List<Transaction> originalList, List<Transaction> comparedResult) {
        ComparisonResultDto comparisonResultDto = new ComparisonResultDto();
        comparisonResultDto.setTotalRecords(originalList.size());
        comparisonResultDto.setMatchingRecords(originalList.size() - comparedResult.size());
        comparisonResultDto.setUnmatchedRecords(comparedResult.size());
        return comparisonResultDto;
    }

    private UnmatchedTransactionsDto createUnmatchedTransactionResult(Transaction transaction) {
        UnmatchedTransactionsDto unmatchedTransactionsDto = new UnmatchedTransactionsDto();

        unmatchedTransactionsDto.setAmount(transaction.getTransactionAmount());
        unmatchedTransactionsDto.setDate(transaction.getTransactionDate());
        unmatchedTransactionsDto.setReference(transaction.getWalletReference());

        return unmatchedTransactionsDto;
    }

    public List<UnmatchedTransactionsDto> createListUnmatchedTransactionResult(List<Transaction> transactions) {
        List<UnmatchedTransactionsDto> unmatchedTransactionsDtos = new ArrayList<>();
        transactions.forEach(transaction -> unmatchedTransactionsDtos.add(createUnmatchedTransactionResult(transaction)));
        return unmatchedTransactionsDtos;
    }
}
