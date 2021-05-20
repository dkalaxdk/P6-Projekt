package DSDLVO.Classes;

public class Utility {
    public static double roundToFourDecimals(double number) {
        return (double) Math.round(number * 10000d) / 10000d;
    }

    public static double roundToOneDecimal(double number) {
        return (double) Math.round(number * 10d) / 10d;
    }

    public static double roundToTwoDecimals(double number) {
        return (double) Math.round(number * 100d) / 100d;
    }
}
