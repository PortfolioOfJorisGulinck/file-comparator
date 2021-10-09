package be.jorisgulinck.filecomparator.mappers;

import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.services.ValidationService;
import be.jorisgulinck.filecomparator.utilities.ParseUtilities;
import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Maps the incoming csv file to a Transaction model object
 */
@Component
@RequiredArgsConstructor
public class CsvMapper {

    private final ValidationService validationService;
    private final ParseUtilities parseUtilities;

    public CsvValidationResult mapAndValidate(InputStream inputStream, String fileName) {
        CsvValidationResult validationResult = new CsvValidationResult();
        CsvParser parser = new CsvParser(new CsvParserSettings());
        parser.beginParsing(inputStream);
        List<String> headers = Arrays.asList(parser.parseNext());
        validationResult = validationService.validateCsvHeaders(validationResult, headers);

        if (!validationResult.isValidationError()) {
            Record record = parser.parseNextRecord();
            while (record != null) {
                Transaction transaction = new Transaction();

                transaction.setId(java.util.UUID.randomUUID().toString());
                transaction.setProfileName(record.getString("ProfileName"));

                validationResult = validationService.validateTransactionDate(validationResult,
                        record.getString("TransactionDate"));
                if (!validationResult.isValidationError()) {
                    transaction.setTransactionDate(mapTransactionDate(record.getString("TransactionDate")));
                }

                transaction.setTransactionAmount(record.getString("TransactionAmount"));
                transaction.setTransactionNarrative(record.getString("TransactionNarrative"));
                transaction.setTransactionDescription(record.getString("TransactionDescription"));

                validationResult = validationService.validateTransactionId(validationResult,
                        record.getString("TransactionID"));
                if (!validationResult.isValidationError()) {
                    transaction.setTransactionId(record.getString("TransactionID"));
                }

                transaction.setTransactionType(record.getString("TransactionType"));
                transaction.setWalletReference(record.getString("WalletReference"));
                transaction.setFileName(fileName);

                validationResult.addTransaction(transaction);
                record = parser.parseNextRecord();
            }
        }
        return validationResult;
    }

    private String mapTransactionDate(String date) {
        LocalDateTime transactionDateTime = LocalDateTime.parse((date), DateTimeFormatter.ofPattern(
                parseUtilities.getDateTimeFormatterString()));
        return transactionDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
