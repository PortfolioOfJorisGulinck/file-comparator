package be.jorisgulinck.filecomparator.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

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

    public Transaction(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * compares the equality of a Transaction object with the properties:
     * transactionId, profileName,transactionDate,transactionAmount,transactionNarrative,
     * transactionDescription,transactionType,walletReference
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(profileName,
                that.profileName) && Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(transactionAmount, that.transactionAmount) && Objects.equals(
                        transactionNarrative, that.transactionNarrative) && Objects.equals(
                                transactionDescription, that.transactionDescription) && Objects.equals(
                                        transactionType, that.transactionType) && Objects.equals(
                                                walletReference, that.walletReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, profileName, transactionDate, transactionAmount,
                transactionNarrative, transactionDescription, transactionType, walletReference);
    }
}