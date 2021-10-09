package be.jorisgulinck.filecomparator.utilities;

import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.models.Transaction;

import java.util.Comparator;

public class SortByRatio implements Comparator<Transaction> {

    @Override
    public int compare(Transaction a, Transaction b) {
        return Integer.parseInt(a.getRatio()) - Integer.parseInt(b.getRatio());
    }
}
