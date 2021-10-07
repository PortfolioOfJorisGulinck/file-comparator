package be.jorisgulinck.filecomparator.mappers;

import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.services.ValidationService;
import be.jorisgulinck.filecomparator.utilities.ParseUtilities;
import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.CsvValidator;
import be.jorisgulinck.filecomparator.validation.ValidationResult;
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

    public ValidationResult mapAndValidate(InputStream inputStream, String fileName) {

        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true); //removes the title row of the csv file
        CsvParser parser = new CsvParser(settings);

        CsvValidationResult validationResult = new CsvValidationResult();

        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            Transaction transaction = new Transaction();
            transaction.setProfileName(record.getString("ProfileName"));

            if (parseUtilities.tryParseDate(record.getString("TransactionDate"))){
                transaction.setTransactionDate(mapTransactionDate(record.getString("TransactionDate")));
            } else {
                transaction.setTransactionDate("Not a valid date");
            }

            transaction.setTransactionAmount(record.getString("TransactionAmount"));
            transaction.setTransactionNarrative(record.getString("TransactionNarrative"));
            transaction.setTransactionDescription(record.getString("TransactionDescription"));
            transaction.setTransactionId(record.getString("TransactionID"));
            transaction.setTransactionType(record.getString("TransactionType"));
            transaction.setWalletReference(record.getString("WalletReference"));
            transaction.setFileName(fileName);

            validationResult.addTransaction(transaction);
        });

        /*

        // TODO fix this bug
        List<String> headers = extractHeaders(inputStream);
        if (validator.validateCsvHeaders(headers)) {
            validationResult.addErrorMessage("Please upload a csv file with correct headers: ProfileName, TransactionDate, " +
                    "TransactionAmount, TransactionNarrative, TransactionDescription, TransactionID, TransactionType, " +
                    "WalletReference");
        }
        */

        return validationService.validateCsvFile(validationResult);
    }

    private List<String> extractHeaders(InputStream inputStream) {
        CsvParser parser = new CsvParser(new CsvParserSettings());
        parser.beginParsing(inputStream);
        List<String> headers = Arrays.asList(parser.parseNext());
        parser.stopParsing();

        return headers;
    }

    private String mapTransactionDate(String date) {
        LocalDateTime transactionDateTime = LocalDateTime.parse((date), DateTimeFormatter.ofPattern(
                parseUtilities.getDateTimeFormatterString()));
        return transactionDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
