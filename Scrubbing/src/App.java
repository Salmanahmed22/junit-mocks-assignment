

import Interfaces.IScrub;
import Interfaces.IScrubDigits;
import Interfaces.IScrubEmails;
import Models.ScrubMode;
import Services.DigitScrubber;
import Services.EmailScrubber;
import Services.MainScrubber;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        IScrubEmails emailScrubber = new EmailScrubber();
        IScrubDigits digitScrubber = new DigitScrubber();
        IScrub mainScrubber = new MainScrubber(digitScrubber, emailScrubber);

        System.out.println("Welcome to Java Scrubber!");
        System.out.println("Choose the mode you want to scrub:");
        System.out.println("1. Scrub digits only");
        System.out.println("2. Scrub emails only");
        System.out.println("3. Scrub both");

        Scanner input = new Scanner(System.in);
        int modeChoice = input.nextInt();
        input.nextLine(); // Consume the leftover newline after nextInt()

        System.out.println("Enter the text to scrub:");
        String textToScrub = input.nextLine();
        //ex: my number is +201114462296 and my salary is 100000$
        // and my email is salman@gmail.com

        String result;
        switch (modeChoice) {
            case 1:
                result = mainScrubber.scrub(textToScrub, ScrubMode.ONLY_DIGITS);
                break;
            case 2:
                result = mainScrubber.scrub(textToScrub, ScrubMode.ONLY_EMAILS);
                break;
            case 3:
                result = mainScrubber.scrub(textToScrub, ScrubMode.FULL_SCRUBBING);
                break;
            default:
                System.out.println("Invalid mode. Please choose 1, 2, or 3.");
                input.close();
                return;
        }

        System.out.println("Scrubbed result: " + result);
        input.close();
    }
}
