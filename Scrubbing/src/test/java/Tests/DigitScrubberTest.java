package Tests;

import Services.DigitScrubber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitScrubberTest {

    private final DigitScrubber digitScrubber = new DigitScrubber();

    // Happy Path
    @Test
    void shouldReplaceDigitsWithX() {
        String input = "My number is 1234";
        String result = digitScrubber.scrub(input);

        assertEquals("My number is XXXX", result);
    }

    // Sad Path
    @Test
    void shouldNotReplacePriceTokens() {
        String input = "Price is 100$ and code 123";
        String result = digitScrubber.scrub(input);

        assertEquals("Price is 100$ and code XXX", result);
    }

    // Edge Cases
    @Test
    void shouldThrowExceptionWhenInputIsNull() {
        assertThrows(NullPointerException.class, () -> {
            digitScrubber.scrub(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenInputIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            digitScrubber.scrub("     ");
        });
    }
}