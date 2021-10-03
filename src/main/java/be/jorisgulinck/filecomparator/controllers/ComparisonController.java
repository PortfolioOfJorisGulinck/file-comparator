package be.jorisgulinck.filecomparator.controllers;

import be.jorisgulinck.filecomparator.dto.TransactionDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for comparing a Transaction between the other transactions of the other csv file
 */
@Controller
public class ComparisonController {

    @GetMapping("/compare")
    public ModelAndView compareFuzzy(@RequestParam("date") String date, @RequestParam("reference") String reference,
            @RequestParam("amount") String amount) {

        TransactionDto comparisonTransaction = new TransactionDto(date, reference, amount);
        List<TransactionDto> fuzzyMatchedList = new ArrayList<>();

        // TODO finishing functionality of controller

        ModelAndView modelAndView = new ModelAndView("second-comparison");
        modelAndView.addObject("comparisonTransaction", comparisonTransaction);
        modelAndView.addObject("fuzzyMatchedList", fuzzyMatchedList);
        return modelAndView;
    }
}
