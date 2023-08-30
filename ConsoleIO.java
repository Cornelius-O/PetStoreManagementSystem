
import java.util.Scanner;

/**
 * Name: Feranmi Oluwasikun
 * NSID: CSB904
 * Student Number: 11315767
 * Class: CMPT 270 03
 */


public class ConsoleIO implements InputOutputInterface {
    public static void main(String[] args) {}

        //Scanner scanner = new Scanner(System.in);
        private static Scanner consoleIn = new Scanner(System.in);


    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        //String asn = consoleIn.nextLine();
        //return asn;
        return consoleIn.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        /**
        System.out.println(prompt);
        int asn = 0;

        try {
            asn = consoleIn.nextInt();
        } catch(Exception e){
            System.out.println("Sorry, please enter an integer");
        }
        consoleIn.nextline();

        return asn;
         */
        System.out.print(prompt);
        int asn = consoleIn.nextInt();
        consoleIn.nextLine();
        return asn;

    }

    @Override
    public int readChoice(String[] options) {
        for (String i : options){
            outputString(i);
        }
        int read = readInt("What would you like: ");
        return read;
    }

    @Override
    public void outputString(String outString) {
        System.out.println(outString);

    }

}
