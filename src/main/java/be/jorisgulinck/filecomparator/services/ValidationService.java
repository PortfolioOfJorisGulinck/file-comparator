package be.jorisgulinck.filecomparator.services;

import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.CsvValidator;
import be.jorisgulinck.filecomparator.validation.FuzzyParamsValidator;
import be.jorisgulinck.filecomparator.validation.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final FuzzyParamsValidator fuzzyParamsValidator;
    private final CsvValidator csvValidator;

    public ValidationResult validateFuzzyParams(String matchingRoutine, String ratio) {
        return fuzzyParamsValidator.validateFuzzyParams(matchingRoutine, ratio);
    }

    public ValidationResult validateCsvFile(CsvValidationResult validationResult) {
        return csvValidator.validateCsvFile(validationResult);
    }
}
