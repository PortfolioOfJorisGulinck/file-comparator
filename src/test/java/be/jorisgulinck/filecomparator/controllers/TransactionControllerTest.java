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
import be.jorisgulinck.filecomparator.mappers.CsvMapper;
import be.jorisgulinck.filecomparator.mappers.DtoMapper;
import be.jorisgulinck.filecomparator.services.ComparisonService;
import be.jorisgulinck.filecomparator.services.ValidationService;
import be.jorisgulinck.filecomparator.utilities.ParseUtilities;
import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.CsvValidator;
import be.jorisgulinck.filecomparator.validation.FuzzyParamsValidator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
class TransactionControllerTest {
    @MockBean
    private ComparisonService comparisonService;

    @MockBean
    private CsvMapper csvMapper;

    @MockBean
    private DtoMapper dtoMapper;

    @Autowired
    private TransactionController transactionController;

    @MockBean
    private ValidationService validationService;

    @Test
    void testUploadData() throws Exception {
        FuzzyParamsValidator fuzzyParamsValidator = new FuzzyParamsValidator();

        ValidationService validationService = new ValidationService(fuzzyParamsValidator, new CsvValidator(null));

        CsvMapper csvMapper = new CsvMapper(validationService, new ParseUtilities());

        ComparisonService comparisonService = new ComparisonService();

        FuzzyParamsValidator fuzzyParamsValidator1 = new FuzzyParamsValidator();

        TransactionController transactionController = new TransactionController(csvMapper, comparisonService,
                new ValidationService(fuzzyParamsValidator1, new CsvValidator(new ParseUtilities())));

        MockMultipartFile file1 = new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"));
        MockMultipartFile file2 = new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"));

        MockHttpSession mockHttpSession = new MockHttpSession();

        ModelAndView actualUploadDataResult = transactionController.uploadData(file1, file2, mockHttpSession);
        assertTrue(actualUploadDataResult.isReference());

        Map<String, Object> model = actualUploadDataResult.getModel();
        assertSame(model, actualUploadDataResult.getModelMap());

        Object getResult = model.get("comparisons");
        assertEquals(2, ((Collection<ComparisonResultDto>) getResult).size());

        ComparisonResultDto getResult1 = ((List<ComparisonResultDto>) getResult).get(0);
        assertEquals(0, getResult1.getTotalRecords());
        assertEquals(0, getResult1.getMatchingRecords());

        ComparisonResultDto getResult2 = ((List<ComparisonResultDto>) getResult).get(1);
        assertEquals(0, getResult2.getTotalRecords());;
        assertEquals(0, getResult2.getMatchingRecords());

        assertEquals(2, mockHttpSession.getValueNames().length);
    }

    @Test
    void testCompareFuzzy() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/compare")
                .param("amount", "foo")
                .param("date", "foo")
                .param("description", "foo")
                .param("file", "foo")
                .param("id", "foo")
                .param("matchingRoutine", "foo")
                .param("name", "foo")
                .param("narrative", "foo")
                .param("ratio", "foo")
                .param("reference", "foo")
                .param("type", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

