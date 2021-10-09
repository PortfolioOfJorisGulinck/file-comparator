package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparator;
import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparatorFactory;
import be.jorisgulinck.filecomparator.comparison.strict.StrictComparator;
import be.jorisgulinck.filecomparator.comparison.strict.StrictEqualsComparator;
import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.dto.FuzzyComparisonResultDto;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.mappers.DtoMapper;
import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.utilities.SortByRatio;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * A service class that compares two collections of {@link Transaction} or a {@link Transaction} object with a collection
 * of {@link Transaction}. For comparing, this class, uses different comparing strategies.
 */
@Service
public class ComparisonService {

    private final DtoMapper dtoMapper;

    public ComparisonService() {
        this.dtoMapper = new DtoMapper();
    }

    /**
     * Creates a comparison between two collections of {@link Transaction}, by using an implementation of {@link StrictComparator}.
     *
     * @param originalList      Collection of {@link Transaction}.
     * @param otherOriginalList Other collection of {@link Transaction}.
     * @return Returns the compared data as a {@link ComparisonResultDto} object.
     */
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

    /**
     * Creates a comparison between a {@link Transaction} and a collection of {@link Transaction}, by using an
     * implementation of {@link FuzzyComparator}.
     *
     * @param id              The id of the {@link Transaction}.
     * @param file            The name of the file of {@link Transaction}.
     * @param listOfFile1     Collection of {@link Transaction} of file1.
     * @param listOfFile2     Collection of {@link Transaction} of file2.
     * @param matchingRoutine The name of the <i>matching strategy</i> used by {@link FuzzyComparatorFactory} for the creation
     *                        of the correct implementation of {@link FuzzyComparator}.
     * @param ratio           The value that determines the precision of the search algorithm.
     * @return Returns the compared data as a {@link FuzzyComparisonResultDto} object.
     */
    public FuzzyComparisonResultDto createFuzzyComparisonResult(String id, String file, List<TransactionDto> listOfFile1,
                                                                List<TransactionDto> listOfFile2, String matchingRoutine, String ratio) {
        TransactionDto comparisonTransaction;
        List<Transaction> fuzzyMatchedDtoList;
        if (file.equals("file1")) {
            comparisonTransaction = listOfFile1
                    .stream()
                    .filter(transactionDto -> id.equals(transactionDto.getId()))
                    .findAny()
                    .orElse(null);

            fuzzyMatchedDtoList = compareFuzzy(
                    comparisonTransaction, listOfFile2, matchingRoutine, Integer.parseInt(ratio));
        } else if (file.equals("file2")) {
            comparisonTransaction = listOfFile2
                    .stream()
                    .filter(transactionDto -> id.equals(transactionDto.getId()))
                    .findAny()
                    .orElse(null);
            fuzzyMatchedDtoList = compareFuzzy(
                    comparisonTransaction, listOfFile1, matchingRoutine, Integer.parseInt(ratio));
        } else {
            comparisonTransaction = new TransactionDto();
            fuzzyMatchedDtoList = new ArrayList<>();
        }

        return new FuzzyComparisonResultDto(comparisonTransaction, dtoMapper.createListOfTransactionDtos(fuzzyMatchedDtoList));
    }

    /**
     * Compares two collections of {@link Transaction} for similarity using an implementation of {@link StrictComparator}.
     *
     * @param listOfTransactions      Collection of {@link Transaction}.
     * @param otherListOfTransactions Other collection of {@link Transaction}.
     * @return Collection of {@link Transaction} that didn't match.
     */
    public List<Transaction> compareStrict(List<Transaction> listOfTransactions, List<Transaction> otherListOfTransactions) {
        StrictComparator strictComparator = new StrictEqualsComparator();
        return strictComparator.compareTransactionsStrict(listOfTransactions, otherListOfTransactions);
    }

    /**
     * Compares two collections of {@link Transaction} for similarity using an implementation of {@link FuzzyComparator}.
     *
     * @param transactionDto        {@link TransactionDto}
     * @param listOfTransactionDtos Collection of {@link TransactionDto}
     * @param matchingRoutine       The name of the <i>matching strategy</i> used by {@link FuzzyComparatorFactory} for
     *                              the creation of the correct implementation of {@link FuzzyComparator}.
     * @param ratio                 The value that determines the precision of the search algorithm.
     * @return Collection of {@link Transaction} that match the search criteria.
     */
    public List<Transaction> compareFuzzy(TransactionDto transactionDto, List<TransactionDto> listOfTransactionDtos, String matchingRoutine, int ratio) {
        FuzzyComparatorFactory fuzzyComparatorFactory = new FuzzyComparatorFactory();
        FuzzyComparator fuzzyComparator = fuzzyComparatorFactory.createFuzzyComparator(matchingRoutine);

        List<Transaction> fuzzyComparedList = fuzzyComparator.compareTransactionsFuzzy(
                dtoMapper.transactionDtoToTransaction(transactionDto),
                dtoMapper.createListOfTransactions(listOfTransactionDtos), ratio);

        List<Transaction> uniqueFuzzyComparedList = dtoMapper.createListOfUniqueTransactions(fuzzyComparedList);

        Comparator c = Collections.reverseOrder(new SortByRatio());
        Collections.sort(uniqueFuzzyComparedList, c);

        return uniqueFuzzyComparedList;
    }


}
