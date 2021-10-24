package be.jorisgulinck.filecomparator.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.models.Transaction;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ComparisonService.class})
@ExtendWith(SpringExtension.class)
class ComparisonServiceTest {
    @Autowired
    private ComparisonService comparisonService;

    @Test
    void testCreateComparisonResult() {
        List<Transaction> originalList = new ArrayList<>();
        List<Transaction> otherList = new ArrayList<>();
        ComparisonResultDto comparisonResult = this.comparisonService.createComparisonResult(originalList, otherList);

        assertEquals(0, comparisonResult.getUnmatchedRecords());
        assertEquals(0, comparisonResult.getTotalRecords());
        assertEquals(0, comparisonResult.getNumberOfDuplicates());
        assertEquals(0, comparisonResult.getMatchingRecords());
    }

    @Test
    void testCreateFuzzyComparisonResult() {
        List<TransactionDto> listOfFile1 = new ArrayList<>();
        List<TransactionDto> listOfFile2 = new ArrayList<>();
        assertEquals(0,
                this.comparisonService
                        .createFuzzyComparisonResult("42", "File", listOfFile1, listOfFile2, "Matching Routine", "80")
                        .getFuzzyComparedList().size());
    }

}

