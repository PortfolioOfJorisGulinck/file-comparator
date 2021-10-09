package be.jorisgulinck.filecomparator.mappers;

import be.jorisgulinck.filecomparator.services.ValidationService;
import be.jorisgulinck.filecomparator.utilities.ParseUtilities;
import be.jorisgulinck.filecomparator.validation.CsvValidationResult;
import be.jorisgulinck.filecomparator.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CsvMapper.class})
@ExtendWith(SpringExtension.class)
class CsvMapperTest {
    String csv;
    MockMultipartFile multipartFile;
    InputStream inputStream;

    @Mock
    ValidationService validationService;

    @Mock
    ParseUtilities parseUtilities;

    @InjectMocks
    CsvMapper csvMapper;

    @BeforeEach
    void setup() throws IOException {
        csv = "ProfileName,TransactionDate,TransactionAmount,TransactionNarrative,TransactionDescription,TransactionID,TransactionType,WalletReference,\n" +
                "Card Campaign,2014-01-11 22:27:44,-20000,*MOLEPS ATM25             MOLEPOLOLE    BW,DEDUCT,0584011808649511,1,P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5,\n" +
                "Card Campaign,2014-01-11 22:39:11,-10000,*MOGODITSHANE2            MOGODITHSANE  BW,DEDUCT,0584011815513406,1,P_NzI1MjA1NjZfMTM3ODczODI3Mi4wNzY5,\n" +
                "Card Campaign,2014-01-11 23:28:11,-5000,CAPITAL BANK              MOGODITSHANE  BW,DEDUCT,0464011844938429,1,P_NzI0NjE1NzhfMTM4NzE4ODExOC43NTYy,";

        multipartFile = new MockMultipartFile("Name", csv.getBytes("UTF-8"));
        inputStream = multipartFile.getInputStream();
    }

    @Test
    void mapAndValidateTest() {
        CsvValidationResult validationResult = Mockito.mock(CsvValidationResult.class);
        when(csvMapper.mapAndValidate(inputStream, "file1")).thenReturn(validationResult);
        ValidationResult result = csvMapper.mapAndValidate(inputStream, "file1");
    }
}