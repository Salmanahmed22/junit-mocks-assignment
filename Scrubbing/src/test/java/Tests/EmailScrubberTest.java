package Tests;

import Services.EmailScrubber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailScrubberTest {

    private final EmailScrubber emailScrubber = new EmailScrubber();

    // Happy Path
    @Test
    void shouldReplaceEmailWithPlaceholder() {
        String input = "Contact me at test@gmail.com";
        String result = emailScrubber.scrub(input);

        assertEquals("Contact me at [EMAIL_HIDDEN]", result);
    }

    @Test
    void shouldHandleMultipleEmails() {
        String input = "a@test.com b@test.com";
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