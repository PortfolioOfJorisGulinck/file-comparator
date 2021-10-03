package be.jorisgulinck.filecomparator.mappers;

import be.jorisgulinck.filecomparator.models.Transaction;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Maps the incoming csv file to a Transaction model object
 */
@Component
public class CsvMapper {

    public List<Transaction> mapToTransactionList(InputStream inputStream, String fileName) throws Exception{

        /**
         * This is a string that the DateTimeFormatter uses to parse a string to a LocalDateTime object
         * The string can be build upon so more formatting styles, coming from the csv file, can be parsed
         */
        String dateTimeFormatterString = "[M/dd/yyyy H:mm][yyyy-MM-dd HH:mm:ss]";

        List<Transaction> transactions = new ArrayList<>();
        CsvParserSettings settings = new CsvParserSettings();

        /** removes the title row of the csv file */
        settings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            Transaction transaction = new Transaction();
            transaction.setProfileName(record.getString("ProfileName"));

            LocalDateTime transactionDateTime = LocalDateTime.parse(record.getString("TransactionDate"),
                          DateTimeFormatter.ofPattern(dateTimeFormatterString));
            transaction.setTransactionDate(transactionDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            transaction.setTransactionAmount(record.getString("TransactionAmount"));
            transaction.setTransactionNarrative(record.getString("TransactionNarrative"));
            transaction.setTransactionDescription(record.getString("TransactionDescription"));

            /**
             * The code below was made because one of the csv files i received had the E notation
             * It transforms the string to a long object, but loses a lot of precision
             */
            // transaction.setTransactionId(Double.valueOf(record.getString("TransactionID")).longValue());

            transaction.setTransactionId(record.getString("TransactionID"));
            transaction.setTransactionType(record.getString("TransactionType"));
            transaction.setWalletReference(record.getString("WalletReference"));
            transaction.setFileName(fileName);

            transactions.add(transaction);
        });

        return transactions;
    }
}
