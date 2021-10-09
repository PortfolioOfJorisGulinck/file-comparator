package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final FuzzyParamsValidator fuzzyParamsValidator;
    private final CsvValidator csvValidator;

    public FuzzyParamsValidationResult validateFuzzyParams(String matchingRoutine, String ratio) {
        return fuzzyParamsValidator.validateFuzzyParams(matchingRoutine, ratio);
    }

    public CsvValidationResult validateCsvHeaders(CsvValidationResult validationResult, List<String> headers) {
        return csvValidator.validateCsvHeaders(headers, validationResult);
    }

    public CsvValidationResult validateTransactionDate(CsvValidationResult validationResult, String date) {
        return csvValidator.validateTransactionDate(date, validationResult);
    }

    public CsvValidationResult validateTransactionId(CsvValidationResult validationResult, String id) {
        return csvValidator.validateTransactionId(id, validationResult);
    }
}
