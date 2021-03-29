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

    private FileParser parser;
    private String InputOneElement = "test/TestFiles/TestInputOneElement.csv";
    private String InputWithoutHashtag = "test/TestFiles/TestInputWithoutHashtag.csv";

    @Nested
    @DisplayName("FileParser.GetNextInput")
    class GetNextInput {
        @Test
        public void GetNextInput_ReturnsAISData(){
            // Arrange
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //assert
            assertNotNull(parser.GetNextInput());
        }

        // Assert
        assertNotNull(parser.GetNextInput());
    }

    @Test
    public void GetNextInput_ReturnsCorrectAISDate(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputTwoElementsWrongOrder.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assert
        assertEquals(parser.GetNextInput().mmsi, 265781000);
    }

    @Test
    public void GetInputList_ReturnsList(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nested
    @DisplayName("FileParser.GetInputList")
    class GetInputList {
        @Test
        public void GetInputList_ReturnsList(){
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }

            assertNotNull(parser.GetInputList());
        }

        @Test
        public void GetInputList_ReturnsListWithOneElement(){
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.size(), 1);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectTimestampString(){
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String actualTimestampString = "23/06/2017 12:34:56";

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).timestampString, actualTimestampString);

        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectDateTime(){
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LocalDateTime actualDateTime = LocalDateTime.of(2017, 6, 23, 12, 34, 56);

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).dateTime, actualDateTime);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectMMSI(){
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int actualMMSI = 211235220;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).mmsi, actualMMSI);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectLatitude(){
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
            double actualLatitude = 91.000000;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).latitude, actualLatitude);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectLongitude(){
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
            double actualLongitude = 181.000000;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).longitude, actualLongitude);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectWidth(){
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int actualWidth = 6;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).width, actualWidth);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectLength(){
            try {
                parser = new FileParser(InputOneElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int actualLength = 23;

            ArrayList<AISData> list = parser.GetInputList();

            assertEquals(list.get(0).length, actualLength);
        }
    }

    @Nested
    @DisplayName("FileParser.CheckFileFormat")
    class CheckFileFormat {
        @Test
        public void CheckFileFormat_HashtagIsSkippedWhenFileStartsWithHashtag(){
            try {
                parser = new FileParser(InputOneElement);

                parser.CheckFileFormat();

                assertTrue(parser.reader.read() != '#');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Test
        public void CheckFileFormat_FileDoesNotStartWithHashtag(){
            try {
                parser = new FileParser(InputWithoutHashtag);

                parser.CheckFileFormat();

                assertTrue(parser.reader.read() == 'T');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
