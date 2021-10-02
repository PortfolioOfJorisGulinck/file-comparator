package be.jorisgulinck.filecomparator.models;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Transaction {
    @Id
    private String transactionId;
    private String profileName;
    private String transactionDate;
    private String transactionAmount;
    private String transactionNarrative;
    private String transactionDescription;
    private String transactionType;
    private String walletReference;
}