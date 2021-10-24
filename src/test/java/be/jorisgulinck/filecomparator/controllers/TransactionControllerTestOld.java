package be.jorisgulinck.filecomparator.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.jorisgulinck.filecomparator.dto.ComparisonResultDto;
import be.jorisgulinck.filecomparator.dto.TransactionDto;
import be.jorisgulinck.filecomparator.mappers.CsvMapper;
import be.jorisgulinck.filecomparator.services.ComparisonService;
import be.jorisgulinck.filecomparator.services.ValidationService;
import be.jorisgulinck.filecomparator.utilities.ParseUtilities;
import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.CsvValidator;
import be.jorisgulinck.filecomparator.validation.FuzzyParamsValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@ContextConfiguration(classes = {TransactionController.class})
@ExtendWith(SpringExtension.class)
class TransactionControllerTestOld {
    @MockBean
    private ComparisonService comparisonService;

    @MockBean
    private CsvMapper csvMapper;

    @Autowired
    private TransactionController transactionController;

    @MockBean
    private ValidationService validationService;

    @BeforeEach
    void setup(){
        validationService = new ValidationService(new FuzzyParamsValidator(), new CsvValidator(null));
        csvMapper = new CsvMapper(validationService, new ParseUtilities());
        comparisonService = new ComparisonService();
        transactionController = new TransactionController(csvMapper, comparisonService, validationService);
    }

    @Test
    void testUploadData() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"));
        MockMultipartFile file2 = new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"));
        ModelAndView actualUploadDataResult = this.transactionController.uploadData(file1, file2, new MockHttpSession());

        assertEquals(
                "ModelAndView [view=\"index\"; model={errorMessages=[Please upload a csv file with correct headers:"
                        + " AAAAAAAAAAAAAAAAAAAAAAAA is not a valid header name.], isError=true}]",
                actualUploadDataResult.toString());
        assertTrue(actualUploadDataResult.isReference());
        assertEquals(1, ((Collection<String>) actualUploadDataResult.getModel().get("errorMessages")).size());
    }

    @Test
    void testCompareFuzzy() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/compare")
                .param("file", "foo")
                .param("id", "foo")
                .param("matchingRoutine", "foo")
                .param("ratio", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testHelpPage() {
        assertTrue((this.transactionController.helpPage().isReference()));
    }

    @Test
    void testComparisonPage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comparison-page");
        MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

}

