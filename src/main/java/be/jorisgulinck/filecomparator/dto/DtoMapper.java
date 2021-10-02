package be.jorisgulinck.filecomparator.dto;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoMapper {

    public ComparisonResultDto createComparisonResult(List<Transaction> originalList, List<Transaction> comparedResult) {
        ComparisonResultDto comparisonResultDto = new ComparisonResultDto();
        comparisonResultDto.setTotalRecords(originalList.size());
        comparisonResultDto.setMatchingRecords(originalList.size() - comparedResult.size());
        comparisonResultDto.setUnmatchedRecords(comparedResult.size());
        return comparisonResultDto;
    }
}
