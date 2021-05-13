package InputTest;

import Dibbidut.Classes.InputManagement.AISData;
import Dibbidut.Classes.InputSimulation.FileParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class FileParserTest {

    private final String InputOneElement = "test/TestFiles/TestInputOneElement.csv";
    private final String InputWithoutHashtag = "test/TestFiles/TestInputWithoutHashtag.csv";
    private final String InputNothing = "test/TestFiles/TestInputNothing.csv";

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
    @DisplayName("FileParser.GetNextInput")
    class GetNextInput{
        @Test
        public void GetNextInput_ThrowsExceptionAtEndOfInput(){
            boolean exceptionThrown = false;
            FileParser parser = createFileParser(InputOneElement);

            parser.GetNextInput();

            try {
                parser.GetNextInput();
            } catch (NoSuchElementException e) {
                exceptionThrown = true;
            }
            assertTrue(exceptionThrown);
        }



        @Test
        public void GetInputList_ReturnsAISDataWithCorrectTimestampString(){
            FileParser parser = createFileParser(InputOneElement);
            String actualTimestampString = "23/06/2017 12:34:56";

            AISData data = parser.GetNextInput();

            assertEquals(actualTimestampString, data.timestampString);

        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectDateTime(){
            FileParser parser = createFileParser(InputOneElement);
            LocalDateTime actualDateTime = LocalDateTime.of(2017, 6, 23, 12, 34, 56);

            AISData data = parser.GetNextInput();

            assertEquals(actualDateTime, data.dateTime);
        }

        @Test
        public void GetNextInput_ReturnsDataWithCorrectMMSI(){
            FileParser parser = createFileParser(InputOneElement);

            AISData data = parser.GetNextInput();

            assertEquals(211235220, data.mmsi);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectLatitude(){
            FileParser parser = createFileParser(InputOneElement);
            double actualLatitude = 91.000000;

            AISData data = parser.GetNextInput();

            assertEquals(actualLatitude, data.latitude);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectLongitude(){
            FileParser parser = createFileParser(InputOneElement);
            double actualLongitude = 181.000000;

            AISData data = parser.GetNextInput();

            assertEquals(actualLongitude, data.longitude);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectWidth(){
            FileParser parser = createFileParser(InputOneElement);
            int actualWidth = 6;

            AISData data = parser.GetNextInput();

            assertEquals(actualWidth, data.width);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectLength(){
            FileParser parser = createFileParser(InputOneElement);
            int actualLength = 23;

            AISData data = parser.GetNextInput();

            assertEquals(actualLength, data.length);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectSOG(){
            FileParser parser = createFileParser(InputOneElement);
            double actualSOG = 8.7;

            AISData data = parser.GetNextInput();

            assertEquals(actualSOG, data.SOG);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectCOG(){
            FileParser parser = createFileParser(InputOneElement);
            double actualCOG = 270.3;

            AISData data = parser.GetNextInput();

            assertEquals(actualCOG, data.COG);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectHeading(){
            FileParser parser = createFileParser(InputOneElement);
            int actualHeading = 180;

            AISData data = parser.GetNextInput();

            assertEquals(actualHeading, data.heading);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectDistanceToFore(){
            FileParser parser = createFileParser(InputOneElement);
            int actualDistance = 11;

            AISData data = parser.GetNextInput();

            assertEquals(actualDistance, data.distanceFore);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectDistanceToAft(){
            FileParser parser = createFileParser(InputOneElement);
            int actualDistance = 12;

            AISData data = parser.GetNextInput();

            assertEquals(actualDistance, data.distanceAft);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectDistanceToPort(){
            FileParser parser = createFileParser(InputOneElement);
            int actualDistance = 3;

            AISData data = parser.GetNextInput();

            assertEquals(actualDistance, data.distancePort);
        }

        @Test
        public void GetInputList_ReturnsAISDataWithCorrectDistanceToStarboard(){
            FileParser parser = createFileParser(InputOneElement);
            int actualDistance = 3;

            AISData data = parser.GetNextInput();

            assertEquals(actualDistance, data.distanceStarboard);
        }



        @Nested
        @DisplayName("FileParser.GetInputList.EmptyCSVFile")
        class FromEmptyFile {

            @Test
            public void GetInputList_EmptyFile_MMSIIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualMMSI = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualMMSI, data.mmsi);
            }

            @Test
            public void GetInputList_EmptyFile_LatitudeIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualLatitude = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualLatitude, data.latitude);
            }

            @Test
            public void GetInputList_EmptyFile_LongitudeIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualLongitude = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualLongitude, data.longitude);
            }

            @Test
            public void GetInputList_EmptyFile_SOGIs0(){
                FileParser parser = createFileParser(InputNothing);
                double actualSOG = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualSOG, data.SOG);
            }

            @Test
            public void GetInputList_EmptyFile_COGIs0(){
                FileParser parser = createFileParser(InputNothing);
                double actualCOG = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualCOG, data.COG);
            }

            @Test
            public void GetInputList_EmptyFile_HeadingIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualHeading = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualHeading, data.heading);
            }


            @Test
            public void GetInputList_EmptyFile_DistanceToForeIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualDistance = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualDistance, data.distanceFore);
            }

            @Test
            public void GetInputList_EmptyFile_DistanceToAftIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualDistance = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualDistance, data.distanceAft);
            }

            @Test
            public void GetInputList_EmptyFile_DistanceToPortIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualDistance = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualDistance, data.distancePort);
            }

            @Test
            public void GetInputList_EmptyFile_DistanceToStarboardIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualDistance = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualDistance, data.distanceStarboard);
            }

            @Test
            public void GetInputList_EmptyFile_LengthIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualLength = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualLength, data.length);
            }

            @Test
            public void GetInputList_EmptyFile_WidthIs0(){
                FileParser parser = createFileParser(InputNothing);
                int actualWidth = 0;

                AISData data = parser.GetNextInput();

                assertEquals(actualWidth, data.width);
            }
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

                assertTrue(parser.reader.read() != 'T');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
