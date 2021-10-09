package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparator;
import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparatorFactory;
import be.jorisgulinck.filecomparator.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A service class that validates the incoming data, using different validation strategies and returns {@link ValidationResult}.
 */
@Service
@RequiredArgsConstructor
public class ValidationService {

    private final FuzzyParamsValidator fuzzyParamsValidator;
    private final CsvValidator csvValidator;

    /**
     * Validates the incoming data, using the {@link FuzzyParamsValidator}.
     *
     * @param matchingRoutine The name of the <i>matching strategy</i> used by {@link FuzzyComparatorFactory} for the creation
     *                        of the correct implementation of {@link FuzzyComparator}.
     * @param ratio           The value that determines the precision of the search algorithm.
     * @return {@link FuzzyParamsValidationResult}
     */
    public FuzzyParamsValidationResult validateFuzzyParams(String matchingRoutine, String ratio) {
        return fuzzyParamsValidator.validateFuzzyParams(matchingRoutine, ratio);
    }

    /**
     * Validates the header names of the csv file, using the {@link CsvValidator}.
     *
     * @param validationResult {@link CsvValidationResult} containing the validation data of previous validations.
     * @param headers          Collection of header Strings.
     * @return {@link CsvValidationResult} containing the updated validation data, including the validation data of
     * previous validations.
     */
    public CsvValidationResult validateCsvHeaders(CsvValidationResult validationResult, List<String> headers) {
        return csvValidator.validateCsvHeaders(headers, validationResult);
    }

    /**
     * Validates the TransactionDate string of the csv file, using the {@link CsvValidator}.
     *
     * @param validationResult {@link CsvValidationResult} containing the validation data of previous validations.
     * @param date             String representing the TransactionDate.
     * @return {@link CsvValidationResult} containing the updated validation data, including the validation data of
     * previous validations.
     */
    public CsvValidationResult validateTransactionDate(CsvValidationResult validationResult, String date) {
        return csvValidator.validateTransactionDate(date, validationResult);
    }

    /**
     * Validates the TransactionId string of the csv file, using the {@link CsvValidator}.
     *
     * @param validationResult {@link CsvValidationResult} containing the validation data of previous validations.
     * @param id               String representing the TransactionID.
     * @return {@link CsvValidationResult} containing the updated validation data, including the validation data of
     * previous validations.
     */
    public CsvValidationResult validateTransactionId(CsvValidationResult validationResult, String id) {
        return csvValidator.validateTransactionId(id, validationResult);
    }
}
