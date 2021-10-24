package be.jorisgulinck.filecomparator.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.utilities.ParseUtilities;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CsvValidator.class, ParseUtilities.class})
@ExtendWith(SpringExtension.class)
class CsvValidatorTest {

    @Autowired
    private CsvValidator csvValidator;

    @MockBean
    private ParseUtilities parseUtilities;

    private CsvValidationResult csvValidationResult;

    @BeforeEach
    void setup() {
        csvValidationResult = new CsvValidationResult();
    }

    @Test
    void testValidateCsvHeaders() {
        List<String> headers = new ArrayList<>();
        assertSame(this.csvValidationResult, this.csvValidator.validateCsvHeaders(headers, this.csvValidationResult));
    }

    @Test
    void testValidateTransactionDate() {
        when(this.parseUtilities.tryParseDate(any())).thenReturn(true);

        assertSame(this.csvValidationResult, this.csvValidator.validateTransactionDate("2020-03-01", this.csvValidationResult));
        verify(this.parseUtilities).tryParseDate(any());
    }

    @Test
    void testValidateTransactionId() {
        CsvValidationResult transactionIdResult = this.csvValidator.validateTransactionId("42", this.csvValidationResult);
        assertSame(this.csvValidationResult, transactionIdResult);

        List<String> errorMessages = transactionIdResult.getErrorMessages();
        assertEquals(1, errorMessages.size());
        assertEquals("There was a problem reading the transaction id.", errorMessages.get(0));
        assertTrue(transactionIdResult.isValidationError());
    }

}

