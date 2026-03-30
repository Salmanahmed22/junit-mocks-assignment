package Services;

import Interfaces.IScrubEmails;

public class EmailScrubber implements IScrubEmails {
    @Override
    public String scrub(String input) throws IllegalArgumentException, NullPointerException {
        if (input == null) {
            throw new NullPointerException("Input cannot be null");
        }
        if (input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be blank");
        }
        // this regex have a problem it should be from 0-9 not 0-0
        return input == null ? "" : input.replaceAll("[a-zA-Z0-0._%+-]+@[a-zA-Z0-0.-]+\\.[a-zA-Z]{2,6}", "[EMAIL_HIDDEN]");
    }
}

