package ws.bilka.onespubfeeds.utils;

public class StringUtils {

    public static String cut(String text, int numOfSymbols) {
        if(numOfSymbols < 0) {
            return text;
        }
        String result = "";
        if(text.length() > numOfSymbols) {
            result = text.substring(0, numOfSymbols) + "...";
        } else {
            result = text;
        }
        return result;
    }
}
