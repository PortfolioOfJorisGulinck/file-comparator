package be.jorisgulinck.filecomparator.controllers;

import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.services.ValidationService;
import be.jorisgulinck.filecomparator.utilities.SortByRatio;
import be.jorisgulinck.filecomparator.mappers.DtoMapper;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.mappers.CsvMapper;
import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.services.ComparisonService;
import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.FuzzyParamsValidationResult;
import be.jorisgulinck.filecomparator.validation.FuzzyParamsValidator;
import be.jorisgulinck.filecomparator.validation.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class TransactionController {

    private final CsvMapper csvMapper;
    private final DtoMapper dtoMapper;
    private final ComparisonService comparisonService;
    private final ValidationService validationService;

    // TODO fix validation of csv
    // TODO finnish testing

    // TODO refactor where possible
    // TODO make proper documentation
    // TODO deploy on heroku

    /**
     * Controller for uploading the two csv files and comparison between the two collections of Transaction
     */
    @PostMapping("/upload")
    public ModelAndView uploadData(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, HttpSession session) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        ValidationResult mapAndValidateResultOfFile1 = csvMapper.mapAndValidate(file1.getInputStream(), "file1");
        ValidationResult mapAndValidateResultOfFile2 = csvMapper.mapAndValidate(file2.getInputStream(), "file2");

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

            Set<TransactionDto> transactionDtosOfList1 = dtoMapper.createListOfUniqueTransactionDtos(filteredListOfFile1);
            Set<TransactionDto> transactionDtosOfList2 = dtoMapper.createListOfUniqueTransactionDtos(filteredListOfFile2);

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
        ModelAndView modelAndView = new ModelAndView();
        List<Transaction> file1list = (List<Transaction>) session.getAttribute("filteredListOfFile1");
        List<Transaction> file2list = (List<Transaction>) session.getAttribute("filteredListOfFile2");

        TransactionDto comparisonTransaction = new TransactionDto(id, name, date, amount, narrative,
                description, type, reference, file, null);

        ValidationResult validationResult = validationService.validateFuzzyParams(matchingRoutine, ratio);
        if (!validationResult.isValidationError()){
            List<Transaction> fuzzyMatchedList;
            if (file.equals("file1")) {
                fuzzyMatchedList = comparisonService.compareFuzzy(
                        dtoMapper.transactionDtoToTransaction(comparisonTransaction), file2list, matchingRoutine, Integer.parseInt(ratio));
            } else {
                fuzzyMatchedList = comparisonService.compareFuzzy(
                        dtoMapper.transactionDtoToTransaction(comparisonTransaction), file1list, matchingRoutine, Integer.parseInt(ratio));
            }

            List<TransactionDto> fuzzyMatchedDtoList = dtoMapper.createListOfTransactionDtos(fuzzyMatchedList);

            Comparator c = Collections.reverseOrder(new SortByRatio());
            Collections.sort(fuzzyMatchedDtoList, c);

            modelAndView.setViewName("second-comparison");
            modelAndView.addObject("comparisonTransaction", comparisonTransaction);
            modelAndView.addObject("fuzzyMatchedList", fuzzyMatchedDtoList);
            modelAndView.addObject("isError", false);
        } else {
            modelAndView.setViewName("second-comparison");
            modelAndView.addObject("errorMessages", validationResult.getErrorMessages());
            modelAndView.addObject("isError", validationResult.isValidationError());
            modelAndView.addObject("comparisonTransaction", comparisonTransaction);
            modelAndView.addObject("fuzzyMatchedList", null);
        }

        return modelAndView;
    }
}
