package be.jorisgulinck.filecomparator.controllers;

import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.mappers.DtoMapper;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.mappers.CsvMapper;
import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.services.ComparisonService;
import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.CsvValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class TransactionController {

    private final CsvMapper csvMapper;
    private final DtoMapper dtoMapper;
    private final ComparisonService comparisonService;

    /**
     * Controller for uploading the two csv files and comparison between the two collections of Transaction
     */
    @PostMapping("/upload")
    public ModelAndView uploadData(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, HttpSession session) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        InputStream inputStream1 = file1.getInputStream();
        InputStream inputStream2 = file2.getInputStream();

        CsvValidationResult mapAndValidateResultOfFile1 = csvMapper.mapAndValidate(inputStream1, "file1");
        CsvValidationResult mapAndValidateResultOfFile2 = csvMapper.mapAndValidate(inputStream2, "file2");

        if (!mapAndValidateResultOfFile1.isValidationError() && !mapAndValidateResultOfFile2.isValidationError()) {

            List<ComparisonResultDto> comparisonResultDtos = new ArrayList<>();

            List<Transaction> filteredListOfFile1 = comparisonService.compareStrict(
                    mapAndValidateResultOfFile1.getValidatedListOfTransactions(),
                    mapAndValidateResultOfFile2.getValidatedListOfTransactions()
            );
            comparisonResultDtos.add(dtoMapper.createComparisonResult(
                    mapAndValidateResultOfFile1.getValidatedListOfTransactions(), filteredListOfFile1)
            );

            List<Transaction> filteredListOfFile2 = comparisonService.compareStrict(
                    mapAndValidateResultOfFile2.getValidatedListOfTransactions(),
                    mapAndValidateResultOfFile1.getValidatedListOfTransactions());

            comparisonResultDtos.add(dtoMapper.createComparisonResult(
                    mapAndValidateResultOfFile2.getValidatedListOfTransactions(), filteredListOfFile2));

            session.setAttribute("filteredListOfFile1", filteredListOfFile1);
            session.setAttribute("filteredListOfFile2", filteredListOfFile2);

            List<TransactionDto> transactionDtosOfList1 = dtoMapper.createListUnmatchedTransactionResult(filteredListOfFile1);
            List<TransactionDto> transactionDtosOfList2 = dtoMapper.createListUnmatchedTransactionResult(filteredListOfFile2);

            modelAndView.setViewName("first-comparison");
            modelAndView.addObject("comparisons", comparisonResultDtos);
            modelAndView.addObject("unmatchedTransactionsOfList1", transactionDtosOfList1);
            modelAndView.addObject("unmatchedTransactionsOfList2", transactionDtosOfList2);

        } else {
            Set<String> errorMessages = new HashSet<>();
            errorMessages.addAll(mapAndValidateResultOfFile1.getErrorMessages());
            errorMessages.addAll(mapAndValidateResultOfFile2.getErrorMessages());
            boolean isError = true;

            modelAndView.setViewName("index");
            modelAndView.addObject("errorMessages", errorMessages);
            modelAndView.addObject("isError", isError);
        }
        return modelAndView;
    }

    @GetMapping("/compare")
    public ModelAndView compareFuzzy(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("date") String date,
            @RequestParam("amount") String amount,
            @RequestParam("narrative") String narrative,
            @RequestParam("description") String description,
            @RequestParam("type") String type,
            @RequestParam("reference") String reference,
            @RequestParam("file") String file,
            @RequestParam("precisionRatio") String ratio,
            @RequestParam("matchingRoutine") String matchingRoutine,
            HttpSession session) {

        List<Transaction> file1list = (List<Transaction>) session.getAttribute("filteredListOfFile1");
        List<Transaction> file2list = (List<Transaction>) session.getAttribute("filteredListOfFile2");

        // functie validating ratio
        // -> als niet juist is standaard 80 geven

        TransactionDto comparisonTransaction = new TransactionDto(id, name, date, amount, narrative,
                description, type, reference, file);

        List<Transaction> fuzzyMatchedList;
        if (file.equals("file1")) {
            fuzzyMatchedList = comparisonService.compareFuzzy(
                    dtoMapper.transactionDtoToTransaction(comparisonTransaction), file2list, Integer.parseInt(ratio));
        } else {
            fuzzyMatchedList = comparisonService.compareFuzzy(
                    dtoMapper.transactionDtoToTransaction(comparisonTransaction), file1list, Integer.parseInt(ratio));
        }

        ModelAndView modelAndView = new ModelAndView("second-comparison");
        modelAndView.addObject("comparisonTransaction", comparisonTransaction);
        modelAndView.addObject("fuzzyMatchedList", fuzzyMatchedList);
        return modelAndView;
    }
}
