package be.jorisgulinck.filecomparator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data transfer class that summarizes the data displayed in the <i>second-comparison</i> view page.
 */
@Getter
@Setter
@AllArgsConstructor
public class FuzzyComparisonResultDto {

    private TransactionDto transactionDto;
    private List<TransactionDto> FuzzyComparedList;

}
