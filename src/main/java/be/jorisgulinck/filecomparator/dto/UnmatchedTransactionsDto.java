package be.jorisgulinck.filecomparator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnmatchedTransactionsDto {

    private String date;
    private String reference;
    private String amount;

}
