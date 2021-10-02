package be.jorisgulinck.filecomparator.controllers;

import be.jorisgulinck.filecomparator.mappers.CsvMapper;
import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.services.ComparisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UploadController {

    private final CsvMapper csvMapper;
    private final ComparisonService comparisonService;

    @PostMapping("/upload")
    public String uploadData(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) throws Exception {
        InputStream inputStream1 = file1.getInputStream();
        List<Transaction> transactionsOfList1 = csvMapper.mapToTransactionList(inputStream1);

        InputStream inputStream2 = file2.getInputStream();
        List<Transaction> transactionsOfList2 = csvMapper.mapToTransactionList(inputStream2);

        System.out.println("TRANSACTIONS OF LIST 1");
        System.out.println("----------------------");
        transactionsOfList1.forEach(System.out::println);
        System.out.println("TRANSACTIONS OF LIST 2");
        System.out.println("----------------------");
        transactionsOfList2.forEach(System.out::println);

        //List<Transaction> filteredList = comparisonService.compareStrict(transactionsOfList1, transactionsOfList2);

        return "comparison";
    }
}
