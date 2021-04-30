package Dibbidut.Classes;

public class Utility {
    public static double roundToFourDecimals(double number) {
        return (double)Math.round(number * 10000d) / 10000d;
    }
}
