package be.jorisgulinck.filecomparator.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.CsvValidator;
import be.jorisgulinck.filecomparator.validation.FuzzyParamsValidationResult;
import be.jorisgulinck.filecomparator.validation.FuzzyParamsValidator;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ValidationService.class, CsvValidator.class, FuzzyParamsValidator.class})
@ExtendWith(SpringExtension.class)
class ValidationServiceTest {
    @MockBean
    private CsvValidator csvValidator;

    @MockBean
    private FuzzyParamsValidator fuzzyParamsValidator;

    @Autowired
    private ValidationService validationService;

    @Test
    void testValidateFuzzyParams() {
        FuzzyParamsValidationResult fuzzyParamsValidationResult = new FuzzyParamsValidationResult();

        when(this.fuzzyParamsValidator.validateFuzzyParams(any(), any())).thenReturn(fuzzyParamsValidationResult);

        assertSame(fuzzyParamsValidationResult, this.validationService.validateFuzzyParams("Matching Routine", "Ratio"));
        verify(this.fuzzyParamsValidator).validateFuzzyParams(any(), any());
    }

    @Test
    void testValidateCsvHeaders() {
        CsvValidationResult csvValidationResult = new CsvValidationResult();
        CsvValidationResult otherValidationResult = new CsvValidationResult();

        when(this.csvValidator.validateCsvHeaders(any(), any())).thenReturn(csvValidationResult);

        assertSame(csvValidationResult, this.validationService.validateCsvHeaders(otherValidationResult, new ArrayList<>()));
        verify(this.csvValidator).validateCsvHeaders(any(), any());
    }

    @Test
    void testValidateTransactionDate() {
        CsvValidationResult csvValidationResult = new CsvValidationResult();

        when(this.csvValidator.validateTransactionDate(any(), any())).thenReturn(csvValidationResult);

        assertSame(csvValidationResult, this.validationService.validateTransactionDate(new CsvValidationResult(), "2020-03-01"));
        verify(this.csvValidator).validateTransactionDate(any(), any());
    }

    @Test
    void testValidateTransactionId() {
        CsvValidationResult csvValidationResult = new CsvValidationResult();

        when(this.csvValidator.validateTransactionId(any(), any())).thenReturn(csvValidationResult);

        assertSame(csvValidationResult, this.validationService.validateTransactionId(new CsvValidationResult(), "42"));
        verify(this.csvValidator).validateTransactionId(any(), any());
    }
}

