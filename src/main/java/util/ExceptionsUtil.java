package util;

public class ExceptionsUtil {
    public static String getShouldBeGreaterException(String argumentName, String value) {
        return String.format("Argument '%s' should be greater than %s", argumentName, value);
    }
    public static String getCouldNotBeNullException(String argumentName) {
        return String.format("Argument '%s' could not be null", argumentName);
    }
    public static String getCouldNotBeEmptyException(String argumentName) {
        return String.format("Argument '%s' could not be empty", argumentName);
    }
    public static String getEntityWithGivenIdDoesNotExist() {
        return "Entity with given Id does not exist";
    }

    public static String getPageNotFoundException() {
        return "Page not found";
    }
    public static String getArgumentIsNotUniqueException(String argumentName) {
        return String.format("Value of given argument '%s' is not unique", argumentName);
    }
}