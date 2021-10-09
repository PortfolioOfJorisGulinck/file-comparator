package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.CsvValidator;
import be.jorisgulinck.filecomparator.validation.FuzzyParamsValidator;
import be.jorisgulinck.filecomparator.validation.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final FuzzyParamsValidator fuzzyParamsValidator;
    private final CsvValidator csvValidator;

    public ValidationResult validateFuzzyParams(String matchingRoutine, String ratio) {
        return fuzzyParamsValidator.validateFuzzyParams(matchingRoutine, ratio);
    }

    public ValidationResult validateCsvHeaders(ValidationResult validationResult, List<String> headers) {
        return csvValidator.validateCsvHeaders(headers, validationResult);
    }

    public ValidationResult validateTransactionDate(ValidationResult validationResult, String date) {
        return csvValidator.validateTransactionDate(date, validationResult);
    }

    public ValidationResult validateTransactionId(ValidationResult validationResult, String id) {
        return csvValidator.validateTransactionId(id, validationResult);
    }
}
