package be.jorisgulinck.filecomparator.mappers;

import be.jorisgulinck.filecomparator.models.Transaction;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvMapper {

    public List<Transaction> mapToTransactionList(InputStream inputStream) {
        List<Transaction> transactions = new ArrayList<>();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            Transaction transaction = new Transaction();
            transaction.setProfileName(record.getString("ProfileName"));
            transaction.setTransactionDate(record.getString("TransactionDate"));
            transaction.setTransactionAmount(record.getString("TransactionAmount"));
            transaction.setTransactionNarrative(record.getString("TransactionNarrative"));
            transaction.setTransactionDescription(record.getString("TransactionDescription"));
            transaction.setTransactionId(record.getString("TransactionID"));
            transaction.setTransactionType(record.getString("TransactionType"));
            transaction.setWalletReference(record.getString("WalletReference"));
            transactions.add(transaction);
        });
        return transactions;
    }
}
