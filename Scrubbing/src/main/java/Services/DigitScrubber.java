package Services;

import Interfaces.IScrubDigits;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitScrubber implements IScrubDigits {

    @Override
    public String scrub(String input) throws IllegalArgumentException, NullPointerException {
        if (input == null) {
            throw new NullPointerException("Input cannot be null");
        }
        if (input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be blank");
        }
        return input.replaceAll("\\d", "X");
    }

    // fixed to make scrub skips prices like this 1000$ or 50.0$
//    @Override
//    public String scrub(String input) throws IllegalArgumentException, NullPointerException {
//        if (input == null) {
//            throw new NullPointerException("Input cannot be null");
//        }
//        if (input.isBlank()) {
//            throw new IllegalArgumentException("Input cannot be blank");
//        }
//        // regex to match full price tokens like 100$ or 100.50$ and individual digits
//        Pattern pattern = Pattern.compile("(\\d+(?:[.,]\\d+)?\\$)|\\d");
//        Matcher matcher = pattern.matcher(input);
//        StringBuffer result = new StringBuffer();
//
//        while (matcher.find()) {
//            // matcher.group(1) is not null if it founds smth like 100$ or 100.50$ .
//            if (matcher.group(1) != null) {
//                matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(1)));
//            } else {
//                matcher.appendReplacement(result, "X");
//            }
//        }
//
//        matcher.appendTail(result);
//        return result.toString();
//    }
}
