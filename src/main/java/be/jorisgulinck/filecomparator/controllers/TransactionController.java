package be.jorisgulinck.filecomparator.controllers;

import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparator;
import be.jorisgulinck.filecomparator.comparison.fuzzy.FuzzyComparatorFactory;
import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.dto.FuzzyComparisonResultDto;
import be.jorisgulinck.filecomparator.services.ValidationService;
import be.jorisgulinck.filecomparator.mappers.DtoMapper;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.mappers.CsvMapper;
import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.services.ComparisonService;
import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.FuzzyParamsValidationResult;
import be.jorisgulinck.filecomparator.validation.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Controller class for mapping requests.
 */
@RequiredArgsConstructor
@Controller
public class TransactionController {

    private final CsvMapper csvMapper;
    private final ComparisonService comparisonService;
    private final ValidationService validationService;

    /**
     * Maps the incoming request for uploading the two csv files and comparison between the two collections of Transaction.
     *
     * @param file1   Csv file one as a MultipartFile.
     * @param file2   Csv file two as a MultipartFile.
     * @param session HttpSession.
     * @return ModelAndView object.
     * @throws Exception
     */
    @PostMapping("/upload")
    public ModelAndView uploadData(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, HttpSession session) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        CsvValidationResult mapAndValidateResultOfFile1 = csvMapper.mapAndValidate(file1.getInputStream(), "file1");
        CsvValidationResult mapAndValidateResultOfFile2 = csvMapper.mapAndValidate(file2.getInputStream(), "file2");

        if (!mapAndValidateResultOfFile1.isValidationError() && !mapAndValidateResultOfFile2.isValidationError()) {
            ComparisonResultDto comparisonResultOfFile1 = comparisonService.createComparisonResult(
                    mapAndValidateResultOfFile1.getValidatedListOfTransactions(),
                    mapAndValidateResultOfFile2.getValidatedListOfTransactions()
            );

            ComparisonResultDto comparisonResultOfFile2 = comparisonService.createComparisonResult(
                    mapAndValidateResultOfFile2.getValidatedListOfTransactions(),
                    mapAndValidateResultOfFile1.getValidatedListOfTransactions()
            );

            session.setAttribute("filteredListOfFile1", comparisonResultOfFile1);
            session.setAttribute("filteredListOfFile2", comparisonResultOfFile2);

            modelAndView.setViewName("first-comparison");
            modelAndView.addObject("comparisonOfFile1", comparisonResultOfFile1);
            modelAndView.addObject("comparisonOfFile2", comparisonResultOfFile2);
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

    /**
     * Maps the incoming request for creating the fuzzy comparison page.
     *
     * @param id              String of id of {@link Transaction}.
     * @param file            String of file name of {@link Transaction}.
     * @param matchingRoutine The name of the <i>matching strategy</i> used by {@link FuzzyComparatorFactory} for the
     *                        creation of the correct implementation of {@link FuzzyComparator}.
     * @param ratio           The value that determines the precision of the search algorithm.
     * @param session         HttpSession.
     * @return ModelAndView object.
     */
    @GetMapping("/compare")
    public ModelAndView compareFuzzy(
            @RequestParam("id") String id,
            @RequestParam("file") String file,
            @RequestParam("precisionRatio") String ratio,
            @RequestParam("matchingRoutine") String matchingRoutine,
            HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        ComparisonResultDto comparisonResultOfFile1 = (ComparisonResultDto) session.getAttribute("filteredListOfFile1");
        ComparisonResultDto comparisonResultOfFile2 = (ComparisonResultDto) session.getAttribute("filteredListOfFile2");

        FuzzyParamsValidationResult validatedFuzzyParams = validationService.validateFuzzyParams(matchingRoutine, ratio);
        if (!validatedFuzzyParams.isValidationError()) {

            FuzzyComparisonResultDto comparisonResult = comparisonService.createFuzzyComparisonResult(
                    id, file, comparisonResultOfFile1.getFilteredList(), comparisonResultOfFile2.getFilteredList(),
                    matchingRoutine, ratio);

            modelAndView.setViewName("second-comparison");
            modelAndView.addObject("comparisonTransaction", comparisonResult.getTransactionDto());
            modelAndView.addObject("fuzzyMatchedList", comparisonResult.getFuzzyComparedList());
            modelAndView.addObject("isError", false);
        } else {
            TransactionDto comparisonTransaction = new TransactionDto(id, file);
            modelAndView.setViewName("second-comparison");
            modelAndView.addObject("errorMessages", validatedFuzzyParams.getErrorMessages());
            modelAndView.addObject("isError", validatedFuzzyParams.isValidationError());
            modelAndView.addObject("comparisonTransaction", comparisonTransaction);
            modelAndView.addObject("fuzzyMatchedList", new ArrayList<TransactionDto>() {
            });
        }

        return modelAndView;
    }

    /**
     * Maps the incoming request for creating the strict comparison page.
     *
     * @param session HttpSession.
     * @return ModelAndView object.
     */
    @GetMapping("/comparison-page")
    public ModelAndView comparisonPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        ComparisonResultDto comparisonResultOfFile1 = (ComparisonResultDto) session.getAttribute("filteredListOfFile1");
        ComparisonResultDto comparisonResultOfFile2 = (ComparisonResultDto) session.getAttribute("filteredListOfFile2");

        if (comparisonResultOfFile1 != null && comparisonResultOfFile2 != null) {
            modelAndView.setViewName("first-comparison");
            modelAndView.addObject("comparisonOfFile1", comparisonResultOfFile1);
            modelAndView.addObject("comparisonOfFile2", comparisonResultOfFile2);
        } else {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    /**
     * Maps the incoming request for creating the help page.
     *
     * @return ModelAndView object.
     */
    @GetMapping("/help")
    public ModelAndView helpPage() {
        return new ModelAndView("help");
    }
}
