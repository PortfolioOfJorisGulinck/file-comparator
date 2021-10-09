package be.jorisgulinck.filecomparator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComparisonResultDto {

    private int totalRecords;
    private int matchingRecords;
    private int unmatchedRecords;
    private int numberOfDuplicates;

    private List<TransactionDto> filteredList;

}
