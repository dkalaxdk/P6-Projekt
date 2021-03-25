
import Dibbidut.Classes.AISData;
import Dibbidut.Classes.FileParser;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileParserTest {

    private FileParser parser;

    @BeforeEach
    public void setUp() throws IOException {
        parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
    }

    @Test
    public void getNextInput_returns_AISData(){
        assertNotNull(parser.getNextInput());
    }

    @Test
    public void getNextInput_returns_correct_AISDate(){

    }

    @Test
    public void getInputList_returns_list(){
        assertNotNull(parser.getInputList());
    }
}

