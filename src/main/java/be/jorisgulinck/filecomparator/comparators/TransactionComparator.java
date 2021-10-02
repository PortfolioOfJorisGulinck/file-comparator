package be.jorisgulinck.filecomparator.comparators;

import be.jorisgulinck.filecomparator.models.Transaction;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionComparator {

    public List<Transaction> compareTransactionsStrict(List<Transaction> list1, List<Transaction> list2) {

        return new ArrayList<>((CollectionUtils.removeAll(list1, list2)));

    }
}
