package be.jorisgulinck.filecomparator.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Model class that represents the values of a financial transaction.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

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

    public Transaction(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Compares the equality of a Transaction object with the properties: transactionId, profileName,transactionDate,
     * transactionAmount,transactionNarrative, transactionDescription,transactionType,walletReference.
     *
     * @param o Object that needs to be compared.
     * @return Boolean value of the comparison. Returns true if the object is equal to the other object.
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

    /**
     * Calculates a numerical value of the object using the properties: transactionId, profileName,transactionDate,
     * transactionAmount,transactionNarrative, transactionDescription,transactionType,walletReference.
     *
     * @return HashCode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(transactionId, profileName, transactionDate, transactionAmount,
                transactionNarrative, transactionDescription, transactionType, walletReference);
    }
}