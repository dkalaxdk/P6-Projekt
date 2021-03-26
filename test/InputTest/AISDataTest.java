package InputTest;

import Dibbidut.Classes.InputManagement.AISData;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class AISDataTest {

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

    @Test
    public void CompareTo_ResultsInCorrectSortingOfArrayList(){
        // todo: lav compareTo metode i AISData
        // lav en liste med to elementer, sorter den og tjek at det er rigtigt

    }

}
