package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparator;
import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparatorFactory;
import be.jorisgulinck.filecomparator.comparison.strict.StrictComparator;
import be.jorisgulinck.filecomparator.comparison.strict.StrictEqualsComparator;
import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.mappers.DtoMapper;
import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.utilities.SortByRatio;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComparisonService {

    private final DtoMapper dtoMapper;

    public ComparisonService() {
        this.dtoMapper = new DtoMapper();
    }

    public ComparisonResultDto createComparisonResult(List<Transaction> originalList, List<Transaction> otherOriginalList) {
        ComparisonResultDto comparisonResultDto = new ComparisonResultDto();

        // Compare the lists of the two files for matching transactions
        List<Transaction> comparedResult = compareStrict(originalList, otherOriginalList);

        // Ads the list with the non matching transactions to the comparisonResultDto
        comparisonResultDto.setFilteredList(dtoMapper.createListOfTransactionDtos(comparedResult));

        comparisonResultDto.setTotalRecords(originalList.size());
        comparisonResultDto.setMatchingRecords(originalList.size() - comparedResult.size());
        comparisonResultDto.setUnmatchedRecords(comparedResult.size());
        comparisonResultDto.setNumberOfDuplicates(originalList.size() - dtoMapper.createListOfUniqueTransactions(originalList).size());

        return comparisonResultDto;
    }

    /** Compares two collections of Transaction for similarity using the TransactionComparator class */
    public List<Transaction> compareStrict(List<Transaction> listOfTransactions1, List<Transaction> listOfTransactions2) {
        StrictComparator strictComparator = new StrictEqualsComparator();
        return strictComparator.compareTransactionsStrict(listOfTransactions1, listOfTransactions2);
    }

    /**
     * Compares a collection of Transaction with a Transaction for similarity in a fuzzy way using
     * the TransactionComparator class
     */
    public List<TransactionDto> compareFuzzy(TransactionDto transactionDto, List<TransactionDto> listOfTransactionDtos, String matchingRoutine, int ratio) {
        FuzzyComparatorFactory fuzzyComparatorFactory = new FuzzyComparatorFactory();
        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.createFuzzyComparator(matchingRoutine);

        List<Transaction> fuzzyComparedList = fuzzyComparator.compareTransactionsFuzzy(
                dtoMapper.transactionDtoToTransaction(transactionDto),
                dtoMapper.createListOfTransactions(listOfTransactionDtos), ratio);

        Comparator c = Collections.reverseOrder(new SortByRatio());
        Collections.sort(fuzzyComparedList, c);

        return dtoMapper.createListOfTransactionDtos(fuzzyComparedList);
    }





}
