package InputTest;

import Dibbidut.Classes.InputManagement.AISData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


public class AISDataTest {

    @Nested
    @DisplayName("AISData.AddDateTime")
    class AddDateTime {
        @Test
        public void AddDateTime_AddsDateTime(){
            // Arrange
            AISData data = new AISData("23/06/2017 12:34:56", 123, 123, 123, 123, 123);
            LocalDateTime time = LocalDateTime.of(2017, 6, 23, 12, 34, 56);

            // Act
            data.AddDateTime();

            // Assert
            assertTrue(data.dateTime.equals(time));
        }
    }

    @Nested
    @DisplayName("AISData.SetBooleans")
    class SetBooleans{
        @Test
        public void SetBooleans_AllBooleansSetToTrue(){
            AISData data = new AISData();
            data.rotString = "1";
            data.sogString = "1";
            data.cogString = "1";
            data.headingString = "1";
            data.distanceForeString = "1";
            data.distanceAftString = "1";
            data.distancePortString = "1";
            data.distanceStarboardString = "1";
            data.widthString = "1";
            data.lengthString = "1";

            data.SetBooleans();

            assertTrue(data.rotIsSet &&
                                data.sogIsSet &&
                                data.cogIsSet &&
                                data.headingIsSet &&
                                data.distanceForeIsSet &&
                                data.distanceAftIsSet &&
                                data.distancePortIsSet &&
                                data.distanceStarboardIsSet &&
                                data.widthIsSet &&
                                data.lengthIsSet);
        }

        @Test
        public void SetBooleans_AllBooleansSetToFalse(){
            AISData data = new AISData();
            data.rotString = null;
            data.sogString = null;
            data.cogString = null;
            data.headingString = null;
            data.distanceForeString = null;
            data.distanceAftString = null;
            data.distancePortString = null;
            data.distanceStarboardString = null;
            data.widthString = null;
            data.lengthString = null;

            data.SetBooleans();

            assertTrue(!data.rotIsSet &&
                    !data.sogIsSet &&
                    !data.cogIsSet &&
                    !data.headingIsSet &&
                    !data.distanceForeIsSet &&
                    !data.distanceAftIsSet &&
                    !data.distancePortIsSet &&
                    !data.distanceStarboardIsSet &&
                    !data.widthIsSet &&
                    !data.lengthIsSet);
        }

    }

    @Nested
    @DisplayName("AISData.CompareTo")
    class CompareTo{
        @Test
        public void CompareTo_ResultsInCorrectSortingOfArrayList(){

            // Arrange
            AISData data1 = new AISData("23/06/2017 12:34:58", 123, 123, 123, 123, 123);
            data1.AddDateTime();
            AISData data2 = new AISData("23/06/2017 12:34:57", 123, 123, 123, 123, 123);
            data2.AddDateTime();

            ArrayList<AISData> list = new ArrayList<>();
            list.add(data1);
            list.add(data2);

            // Act
            Collections.sort(list);

            // Assert
            assertTrue(list.get(0).equals(data2) && list.get(1).equals(data1));
        }

        @Test
        public void CompareTo_ResultsInCorrectSortingOfArrayList_SameValue(){

            // Arrange
            AISData data1 = new AISData("23/06/2017 12:34:58", 123, 123, 123, 123, 123);
            data1.AddDateTime();
            AISData data2 = new AISData("23/06/2017 12:34:58", 123, 123, 123, 123, 123);
            data2.AddDateTime();

            ArrayList<AISData> list = new ArrayList<>();
            list.add(data1);
            list.add(data2);

            // Act
            Collections.sort(list);

            // Assert
            assertTrue(list.get(0).equals(data1) && list.get(1).equals(data2));
        }
    }
}
