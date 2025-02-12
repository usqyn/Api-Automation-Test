package functionsfortest;

public class NumberToStringConverter {
    public String convert(int number) {
        switch (number) {
            case 0: return "zero";
            case 1: return "one";
            case 2: return "two";
            case 3: return "three";
            default: return "unknown";
        }
    }
}
