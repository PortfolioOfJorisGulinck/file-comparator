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
import lombok.NoArgsConstructor;
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
        // CsvParserSettings settings = new CsvParserSettings();
        //settings.setHeaderExtractionEnabled(true); //removes the title row of the csv file
        //CsvParser parser = new CsvParser(settings);

        ValidationResult validationResult = new CsvValidationResult();
        CsvParser parser = new CsvParser(new CsvParserSettings());
        parser.beginParsing(inputStream);
        List<String> headers = Arrays.asList(parser.parseNext());
        validationResult = validationService.validateCsvHeaders(validationResult, headers);

        if (!validationResult.isValidationError()) {
            Record record = parser.parseNextRecord();
            while (record != null) {

                //List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
                // parseAllRecords.forEach(record -> {
                Transaction transaction = new Transaction();
                transaction.setProfileName(record.getString("ProfileName"));

                validationResult = validationService.validateTransactionDate(validationResult,
                        record.getString("TransactionDate"));
                if (!validationResult.isValidationError()) {
                    transaction.setTransactionDate(mapTransactionDate(record.getString("TransactionDate")));
                }
                //updatedValidationResult2.getErrorMessages().forEach(validationResult::addErrorMessage);

                transaction.setTransactionAmount(record.getString("TransactionAmount"));
                transaction.setTransactionNarrative(record.getString("TransactionNarrative"));
                transaction.setTransactionDescription(record.getString("TransactionDescription"));

                validationResult = validationService.validateTransactionId(validationResult,
                        record.getString("TransactionID"));
                if (!validationResult.isValidationError()) {
                    transaction.setTransactionId(record.getString("TransactionID"));
                }
               // updatedValidationResult3.getErrorMessages().forEach(validationResult::addErrorMessage);

                transaction.setTransactionType(record.getString("TransactionType"));
                transaction.setWalletReference(record.getString("WalletReference"));
                transaction.setFileName(fileName);

                validationResult.addTransaction(transaction);
                record = parser.parseNextRecord();
            }
        }

        //updatedValidationResult.getErrorMessages().forEach(validationResult::addErrorMessage);
        return validationResult;
    }

    /*
    public ValidationResult mapHeaders(InputStream inputStream) {
        ValidationResult validationResult = new CsvValidationResult();
        CsvParser parser = new CsvParser(new CsvParserSettings());
        parser.beginParsing(inputStream);
        List<String> headers = Arrays.asList(parser.parseNext());
        parser.stopParsing();

        return validationService.validateCsvHeaders(validationResult, headers);
    }
    */

    private String mapTransactionDate(String date) {
        LocalDateTime transactionDateTime = LocalDateTime.parse((date), DateTimeFormatter.ofPattern(
                parseUtilities.getDateTimeFormatterString()));
        return transactionDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
