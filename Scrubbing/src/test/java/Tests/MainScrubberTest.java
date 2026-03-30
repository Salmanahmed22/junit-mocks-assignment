package Tests;

import Interfaces.IScrubDigits;
import Interfaces.IScrubEmails;
import Models.ScrubMode;
import Services.MainScrubber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainScrubberTest {

    private IScrubDigits digitScrubber;
    private IScrubEmails emailScrubber;
    private MainScrubber mainScrubber;

    @BeforeEach
    void setup() {
        digitScrubber = mock(IScrubDigits.class);
        emailScrubber = mock(IScrubEmails.class);
        mainScrubber = new MainScrubber(digitScrubber, emailScrubber);
    }

    // ONLY_DIGITS behavior
    @Test
    void shouldCallDigitScrubberOnly() {
        when(digitScrubber.scrub("test")).thenReturn("XXX");

        String result = mainScrubber.scrub("test", ScrubMode.ONLY_DIGITS);

        assertEquals("XXX", result);

        verify(digitScrubber, times(1)).scrub("test");
        verify(emailScrubber, never()).scrub(any());
    }

    // \ONLY_EMAILS behavior
    @Test
    void shouldCallEmailScrubberOnly() {
        when(emailScrubber.scrub("test")).thenReturn("[EMAIL_HIDDEN]");

        String result = mainScrubber.scrub("test", ScrubMode.ONLY_EMAILS);

        assertEquals("[EMAIL_HIDDEN]", result);

        verify(emailScrubber, times(1)).scrub("test");
        verify(digitScrubber, never()).scrub(any());
    }

    // FULL_SCRUBBING behavior
    @Test
    void shouldCallBothScrubbersInOrder() {
        when(digitScrubber.scrub("test123")).thenReturn("testXXX");
        when(emailScrubber.scrub("testXXX")).thenReturn("final");

        String result = mainScrubber.scrub("test123", ScrubMode.FULL_SCRUBBING);

        assertEquals("final", result);

        verify(digitScrubber, times(1)).scrub("test123");
        verify(emailScrubber, times(1)).scrub("testXXX");
    }

    // Exception handling
    @Test
    void shouldReturnNullWhenExceptionOccurs() {
        when(digitScrubber.scrub(null)).thenThrow(new NullPointerException());

        String result = mainScrubber.scrub(null, ScrubMode.ONLY_DIGITS);

        assertNull(result);
    }
}