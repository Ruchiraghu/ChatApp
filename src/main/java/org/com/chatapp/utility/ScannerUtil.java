package org.com.chatapp.utility;

import java.util.Scanner;

public class ScannerUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getInt(String prompt) {
        System.out.print(prompt + " ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // clear invalid input
            System.out.print(prompt + " ");
        }
        return scanner.nextInt();
    }

    public static Long getLong(String prompt) {
        System.out.println(prompt+" ");
        return scanner.nextLong();

    }
}
