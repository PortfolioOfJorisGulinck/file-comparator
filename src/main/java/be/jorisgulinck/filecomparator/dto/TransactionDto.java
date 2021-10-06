package be.jorisgulinck.filecomparator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private String transactionId;
    private String profileName;
    private String transactionDate;
    private String transactionAmount;
    private String transactionNarrative;
    private String transactionDescription;
    private String transactionType;
    private String walletReference;
    private String fileName;
    private String ratio;


}
