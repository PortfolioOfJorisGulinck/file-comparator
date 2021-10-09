package be.jorisgulinck.filecomparator.dto;

import be.jorisgulinck.filecomparator.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer class of {@link Transaction}
 */
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
