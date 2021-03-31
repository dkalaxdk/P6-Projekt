package InputTest;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.FileParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FileParserTest {

    private String InputOneElement = "test/TestFiles/TestInputOneElement.csv";
    private String InputThreeElements = "test/TestFiles/TestInputThreeElements.csv";
    private String InputWithoutHashtag = "test/TestFiles/TestInputWithoutHashtag.csv";
    private String InputWithWrongOrder = "test/TestFiles/TestInputTwoElementsWrongOrder.csv";

    /*
    Creates and returns a new FileParser called with the given string.
    Using this removes the need to have a try-catch block in all tests

    This returns null if there is an error
    Another possibility is adding the exception to the method signatures,
    this would cause the tests to fail when an error is thrown rather than
    as a result of the returned fileParser being null
     */
    private FileParser createFileParser(String inputFile) {
        FileParser fileParser = null;
        try {
            fileParser = new FileParser(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileParser;
    }



    @Nested
    @DisplayName("FileParser.GetInputList")
    class GetInputList {

        @Test
        public void GetInputList_DoesNotReturnNull(){
            FileParser parser = createFileParser(InputOneElement);

            assertNotNull(parser.GetInputList());
        }

        @Test
        public void GetInputList_ReturnsListWithOneElement(){
            FileParser parser = createFileParser(InputOneElement);

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.size(), 1);
        }

        @Test
        public void GetInputList_ReturnsListWithMultipleElements() {
            FileParser parser = createFileParser(InputThreeElements);

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.size(), 3);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectTimestampString(){
            FileParser parser = createFileParser(InputOneElement);
            String actualTimestampString = "23/06/2017 12:34:56";

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).timestampString, actualTimestampString);

        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectDateTime(){
            FileParser parser = createFileParser(InputOneElement);
            LocalDateTime actualDateTime = LocalDateTime.of(2017, 6, 23, 12, 34, 56);

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).dateTime, actualDateTime);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectMMSI(){
            FileParser parser = createFileParser(InputOneElement);
            int actualMMSI = 211235220;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).mmsi, actualMMSI);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectLatitude(){
            FileParser parser = createFileParser(InputOneElement);
            double actualLatitude = 91.000000;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).latitude, actualLatitude);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectLongitude(){
            FileParser parser = createFileParser(InputOneElement);
            double actualLongitude = 181.000000;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).longitude, actualLongitude);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectWidth(){
            FileParser parser = createFileParser(InputOneElement);
            int actualWidth = 6;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).width, actualWidth);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectLength(){
            FileParser parser = createFileParser(InputOneElement);
            int actualLength = 23;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).length, actualLength);
        }
    }

    @Nested
    @DisplayName("FileParser.CheckFileFormat")
    class CheckFileFormat {
        @Test
        public void SkipHashtagIfPresent_HashtagIsSkippedWhenFileStartsWithHashtag(){
            try {
                FileParser parser = new FileParser(InputOneElement);

                parser.SkipHashtagIfPresent();

                assertTrue(parser.reader.read() != '#');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Test
        public void SkipHashtagIfPresent_FileDoesNotStartWithHashtag(){
            try {
                FileParser parser = new FileParser(InputWithoutHashtag);

                parser.SkipHashtagIfPresent();

                assertTrue(parser.reader.read() == 'T');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
