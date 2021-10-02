package be.jorisgulinck.filecomparator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComparisonResultDto {

    private int totalRecords;
    private int matchingRecords;
    private int unmatchedRecords;

}
