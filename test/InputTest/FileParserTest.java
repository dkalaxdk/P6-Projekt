package InputTest;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.FileParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FileParserTest {

    private FileParser parser;

    @Test
    public void GetNextInput_ReturnsAISData(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //assert
        assertNotNull(parser.GetNextInput());
    }

    @Test
    public void GetNextInput_ReturnsCorrectAISDate(){

    }

    @Test
    public void GetInputList_ReturnsList(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assert
        assertNotNull(parser.GetInputList());
    }

    @Test
    public void GetInputList_ReturnsListWithOneElement(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Act
        ArrayList<AISData> list = parser.GetInputList();

        // Assert
        assertEquals(list.size(), 1);
    }

    @Test
    public void GetInputList_ReturnsAISDataWithCorrectTimestampString(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String actualTimestampString = "23/06/2017 12:34:56";

        // Act
        ArrayList<AISData> list = parser.GetInputList();

        // Assert
        assertEquals(list.get(0).timestampString, actualTimestampString);

    }

    @Test
    public void GetInputList_ReturnsAISDataWithCorrectDateTime(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalDateTime actualDateTime = LocalDateTime.of(2017, 6, 23, 12, 34, 56);

        // Act
        ArrayList<AISData> list = parser.GetInputList();

        // Assert
        assertEquals(list.get(0).dateTime, actualDateTime);
    }

    @Test
    public void GetInputList_ReturnsAISDataWithCorrectMMSI(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int actualMMSI = 211235220;

        // Act
        ArrayList<AISData> list = parser.GetInputList();

        // Assert
        assertEquals(list.get(0).mmsi, actualMMSI);
    }

    @Test
    public void GetInputList_ReturnsAISDataWithCorrectLatitude(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        double actualLatitude = 91.000000;

        // Act
        ArrayList<AISData> list = parser.GetInputList();

        // Assert
        assertEquals(list.get(0).latitude, actualLatitude);
    }

    @Test
    public void GetInputList_ReturnsAISDataWithCorrectLongitude(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        double actualLongitude = 181.000000;

        // Act
        ArrayList<AISData> list = parser.GetInputList();

        // Assert
        assertEquals(list.get(0).longitude, actualLongitude);
    }

    @Test
    public void GetInputList_ReturnsAISDataWithCorrectWidth(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int actualWidth = 6;

        // Act
        ArrayList<AISData> list = parser.GetInputList();

        // Assert
        assertEquals(list.get(0).width, actualWidth);
    }

    @Test
    public void GetInputList_ReturnsAISDataWithCorrectLength(){
        // Arrange
        try {
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int actualLength = 23;

        // Act
        ArrayList<AISData> list = parser.GetInputList();

        // Assert
        assertEquals(list.get(0).length, actualLength);
    }



    @Test
    public void CheckFileFormat_HashtagIsSkippedWhenFileStartsWithHashtag(){

        try {
            // Arrange
            parser = new FileParser("test/TestFiles/TestInputOneElement.csv");

            // Act
            parser.CheckFileFormat();

            // Assert
            assertTrue(parser.reader.read() != '#');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CheckFileFormat_FileDoesNotStartWithHashtag(){
        try {
            // Arrange
            parser = new FileParser("test/TestFiles/TestInputWithoutHashtag.csv");

            // Act
            parser.CheckFileFormat();

            // Assert
            assertTrue(parser.reader.read() == 'T');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
