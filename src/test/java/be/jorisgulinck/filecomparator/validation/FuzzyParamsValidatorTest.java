package be.jorisgulinck.filecomparator.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FuzzyParamsValidator.class})
@ExtendWith(SpringExtension.class)
class FuzzyParamsValidatorTest {

    @Autowired
    private FuzzyParamsValidator fuzzyParamsValidator;

    @Test
    @DisplayName("Testing 'validateFuzzyParams' with error messages")
    void testValidateFuzzyParams() {
        FuzzyParamsValidationResult validationResult = this.fuzzyParamsValidator.validateFuzzyParams("Matching Routine", "Ratio");
        List<String> errorMessages = validationResult.getErrorMessages();

        assertEquals(1, errorMessages.size());
        assertEquals("There was a problem with parsing of the Fuzzy search parameters.", errorMessages.get(0));
        assertTrue(validationResult.isValidationError());
    }


    @Test
    @DisplayName("Testing 'validateFuzzyParams' without error messages")
    void testValidateFuzzyParams2() {
        FuzzyParamsValidationResult validationResult = this.fuzzyParamsValidator.validateFuzzyParams("Simple Ratio", "75");

        assertTrue(validationResult.getErrorMessages().isEmpty());
        assertFalse(validationResult.isValidationError());
    }

}

