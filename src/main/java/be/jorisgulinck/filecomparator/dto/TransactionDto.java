package be.jorisgulinck.filecomparator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private String id;
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

    public TransactionDto(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }
}
