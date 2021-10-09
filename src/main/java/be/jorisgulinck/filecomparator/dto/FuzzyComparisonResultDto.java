package be.jorisgulinck.filecomparator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FuzzyComparisonResultDto {

    private TransactionDto transactionDto;
    private List<TransactionDto> FuzzyComparedList;

}
