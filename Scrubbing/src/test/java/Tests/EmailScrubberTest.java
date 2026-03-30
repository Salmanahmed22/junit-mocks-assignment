package Tests;

import Services.EmailScrubber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailScrubberTest {

    private final EmailScrubber emailScrubber = new EmailScrubber();

    // Happy Path
    @Test
    void shouldReplaceEmailThatContainsZeroWithPlaceholder() {
        String input = "Contact me at test0@gmail.com";
        String result = emailScrubber.scrub(input);

        assertEquals("Contact me at [EMAIL_HIDDEN]", result);
    }
    // Sad Path
    @Test
    void shouldReplaceEmailWithAnyDigitPlaceholder() {
        String input = "Contact me at test1@gmail.com";
        String result = emailScrubber.scrub(input);

        assertEquals("Contact me at [EMAIL_HIDDEN]", result);
    }

    @Test
    void shouldHandleMultipleEmails() {
        String input = "0@test.com 0@test.com";
        String result = emailScrubber.scrub(input);

        assertEquals("[EMAIL_HIDDEN] [EMAIL_HIDDEN]", result);
    }

    // Edge Cases
    @Test
    void shouldThrowExceptionWhenInputIsNull() {
        assertThrows(NullPointerException.class, () -> {
            emailScrubber.scrub(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenInputIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            emailScrubber.scrub("   ");
        });
    }
}