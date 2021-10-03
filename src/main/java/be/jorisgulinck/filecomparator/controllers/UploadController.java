package be.jorisgulinck.filecomparator.controllers;

import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.dto.DtoMapper;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.mappers.CsvMapper;
import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.services.ComparisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UploadController {

    private final CsvMapper csvMapper;
    private final DtoMapper dtoMapper;
    private final ComparisonService comparisonService;

    /**
     * Controller for uploading the two csv files and returns the first comparison between the two collections of Transaction
     */
    @PostMapping("/upload")
    public ModelAndView uploadData(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) throws Exception {
        List<ComparisonResultDto> comparisonResultDtos = new ArrayList<>();

        InputStream inputStream1 = file1.getInputStream();
        List<Transaction> transactionsOfList1 = csvMapper.mapToTransactionList(inputStream1);

        InputStream inputStream2 = file2.getInputStream();
        List<Transaction> transactionsOfList2 = csvMapper.mapToTransactionList(inputStream2);

        List<Transaction> filteredListOfList1 = comparisonService.compareStrict(transactionsOfList1, transactionsOfList2);
        comparisonResultDtos.add(dtoMapper.createComparisonResult(transactionsOfList1, filteredListOfList1));

        List<Transaction> filteredListOfList2 = comparisonService.compareStrict(transactionsOfList2, transactionsOfList1);
        comparisonResultDtos.add(dtoMapper.createComparisonResult(transactionsOfList2, filteredListOfList2));

        List<TransactionDto> transactionDtosOfList1 = dtoMapper.createListUnmatchedTransactionResult(filteredListOfList1);
        List<TransactionDto> transactionDtosOfList2 = dtoMapper.createListUnmatchedTransactionResult(filteredListOfList2);

        ModelAndView modelAndView = new ModelAndView("first-comparison");
        modelAndView.addObject("comparisons", comparisonResultDtos);
        modelAndView.addObject("unmatchedTransactionsOfList1", transactionDtosOfList1);
        modelAndView.addObject("unmatchedTransactionsOfList2", transactionDtosOfList2);
        return modelAndView;
    }
}
