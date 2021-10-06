package be.jorisgulinck.filecomparator.utilities;

import be.jorisgulinck.filecomparator.dto.TransactionDto;

import java.util.Comparator;

public class SortByRatio implements Comparator<TransactionDto> {

    @Override
    public int compare(TransactionDto a, TransactionDto b) {
        return Integer.parseInt(a.getRatio()) - Integer.parseInt(b.getRatio());
    }
}
