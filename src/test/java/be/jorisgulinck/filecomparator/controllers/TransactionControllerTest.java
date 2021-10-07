package be.jorisgulinck.filecomparator.controllers;

import be.jorisgulinck.filecomparator.models.Transaction;
import be.jorisgulinck.filecomparator.services.ComparisonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @MockBean
    private ComparisonService comparisonService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private List<Transaction> listOfTransactions1;
    private List<Transaction> listOfTransactions2;
    private List<Transaction> filteredList;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();

        listOfTransactions1 = new ArrayList<>(Arrays.asList(new Transaction("ID1"), new Transaction("ID2"), new Transaction("ID3")));
        listOfTransactions2 = new ArrayList<>(Arrays.asList(new Transaction("ID1"), new Transaction("ID 2"), new Transaction("ID 3")));
        filteredList = new ArrayList<>(Arrays.asList(new Transaction("ID2")));
    }

    @Test
    void uploadData() {
        when(comparisonService.compareStrict(listOfTransactions1, listOfTransactions2)).thenReturn(filteredList);

        //TODO implement logic

        /*
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/upload")
                        .content() //Lookup what to do with content
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

         */
    }

    @Test
    void compareFuzzy() {
    }
}