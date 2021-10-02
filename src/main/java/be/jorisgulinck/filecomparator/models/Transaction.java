package be.jorisgulinck.filecomparator.models;

import lombok.Data;

import javax.persistence.Id;
import java.util.Objects;

@Data
public class Transaction {

    @Id
    private long transactionId;
    private String profileName;
    private String transactionDate;
    private String transactionAmount;
    private String transactionNarrative;
    private String transactionDescription;
    private String transactionType;
    private String walletReference;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionDate, that.transactionDate) && Objects.equals(transactionAmount,
                that.transactionAmount) && Objects.equals(walletReference, that.walletReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionDate, transactionAmount, walletReference);
    }

}