package divingcalculations;

import java.util.Scanner;

/**
 * This Class contains loops to determine whether the user input is valid or not.
 * and use switch cases to call different methods depending on the user input.
 *
 * @QingyunChen
 * @9/21/2022
 */
public class Driver {
    public static void main(String[] args) {
        DiveBroker diveB = new DiveBroker();
        Scanner scan = new Scanner(System.in);
        String option;
        String choice;
        System.out.println("Welcome to the Dive Formula Calculator");
        //System.out.println();

        //use do while to loop the menu if user types y/Y
        do {
            System.out.println();
            System.out.print("Select which calculation you wish to perform\n" +
                    "1. HELP\n" +
                    "2. MOD\n" +
                    "3. SMOD\n" +
                    "4. BM\n" +
                    "5. PP\n" +
                    "6. EAD\n" +
                    "7. PPT\n" +
                    "8. EADT\n" +
                    "9. Exit\n" +
                    "Enter 1 to 9: ");
            option = scan.next();

            //the system will require re-enter if the input is not ranged from 1-7
            while (!option.matches("[1-9]")) {
                System.out.print("Invalid Option. Please reenter: ");
                option = scan.next();
            }

            //valid user input will go through different cases
            switch (option) {
                case "1":
                    diveB.helpOption();
                    break;
                case "2":
                    diveB.modOption();
                    break;
                case "3":
                    diveB.smodOption();
                    break;
                case "4":
                    diveB.bmOption();
                    break;
                case "5":
                    diveB.ppOption();
                    break;
                case "6":
                    diveB.eadOption();
                    break;
                case "7":
                    diveB.pptOption();
                    break;
                case "8":
                    diveB.eadtOption();
                    break;
                case "9":
                    break;
            }

            System.out.println();
            System.out.print("Would you like to perform another calculation (y/n)? ");
            choice = scan.next();
        } while (choice.equalsIgnoreCase("y"));
    }
}
