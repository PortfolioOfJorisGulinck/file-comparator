package be.jorisgulinck.filecomparator.mappers;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.jorisgulinck.filecomparator.services.ValidationService;
import be.jorisgulinck.filecomparator.utilities.ParseUtilities;
import be.jorisgulinck.filecomparator.validation.CsvValidationResult;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CsvMapper.class, ParseUtilities.class, ValidationService.class})
@ExtendWith(SpringExtension.class)
class CsvMapperTest {
    @Autowired
    private CsvMapper csvMapper;

    @MockBean
    private ValidationService validationService;

    @Test
    void testMapAndValidate() throws UnsupportedEncodingException {
        CsvValidationResult csvValidationResult = new CsvValidationResult();

        when(this.validationService.validateCsvHeaders(any(), any())).thenReturn(csvValidationResult);

        assertSame(csvValidationResult, this.csvMapper
                .mapAndValidate(new ByteArrayInputStream("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8")), "foo.txt"));
        verify(this.validationService).validateCsvHeaders(any(), any());
    }
}

