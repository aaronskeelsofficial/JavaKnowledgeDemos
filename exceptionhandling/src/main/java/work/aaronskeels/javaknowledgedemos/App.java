package work.aaronskeels.javaknowledgedemos;

import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.filechooser.FileSystemView;

public class App 
{
    public static void main( String[] args )
    {
        // Concept 1: Basics - Longest form code with most intricate handling ability
        exemplifyBasics();
        // Concept 2: Multi-Catch - Shorter form code with limits/rules regarding handling ability
        exemplifyMultiCatch();
        // Concept 3: Finally - Merge forking behaviors (but be careful not to IGNORE error with this)
        exemplifyFinally();
        // Concept 4: Try-With-Resources - Aside from two minimal things to be cautious of, always use this
        exemplifyTryWithResources();
        // Concept 5: Custom Exceptions - Custom exceptions can be interacted with in native-like fashion
        exemplifyCustomException();
    }

    /*
     * The most basic form of exception handling is the try-catch block
     */
    public static void exemplifyBasics() {
        Scanner in = new Scanner(System.in);
        try {
            in.nextLine();
            in.close();
        } catch (NoSuchElementException e1) {
            in.close();
            e1.printStackTrace();
        } catch (IllegalStateException e2) {
            in.close();
            e2.printStackTrace();
        }
    }

    /*
     * A more intricate form of exception handling is the try-multi-catch block.
     * Important Note: "e" will take the type of the closest shared class inherited by
     *  ALL exception types listed within the block. This limits oneself to methods and fields
     *  found within that possibly deeply rudamentary Exception class.
     */
    public static void exemplifyMultiCatch() {
        Scanner in = new Scanner(System.in);
        try {
            in.nextLine();
            in.close();
        } catch (NoSuchElementException | IllegalStateException e) {
            in.close();
            e.printStackTrace();
        }
    }

    /*
     * Another more intricate form of exception handling is including the "finally" block,
     * which contains code that will run regardless of whether or not an exception is thrown.
     */
    public static void exemplifyFinally() {
        Scanner in = new Scanner(System.in);
        try {
            in.nextLine();
        } catch (NoSuchElementException | IllegalStateException e) {
            e.printStackTrace();
        } finally {
            System.out.println("This is the finally block which runs whether or not an exception is thrown!");
            in.close();
        }
    }

    /*
     * When opening a resource that needs error handling, it is best practice to use try-with-resources
     * as this will automatically close the resource whether or not an error is thrown. (This has saved
     * me many times).
     * Note: If both the try block and the close() method of a resource throw exceptions, the exception
     *  from the try block is suppressed and the close() exception is propogated.
     * Note: Resources are closed in reverse order of their declaration. If order is important, be cautious.
     */
    public static void exemplifyTryWithResources() {
        try (
            Scanner in = new Scanner(System.in);
            FileReader fr = new FileReader(FileSystemView.getFileSystemView().getDefaultDirectory().getPath())
        ) {
            in.nextLine();
        } catch (NoSuchElementException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Although native exceptions exist, one can also make custom exceptions and interact with them in
     * a native-like fashion.
     */
    public static void exemplifyCustomException() {
        class CustomException extends Exception {
            public CustomException(String s) {
                super(s);
            }
        }

        class Exemplifier {
            public static void run() {
                try {
                    performOperation();
                } catch (CustomException e) {
                    System.err.println("Custom Exception Caught: " + e.getMessage());
                }
            }
            public static void performOperation() throws CustomException {
                throw new CustomException("A custom exception has been thrown!");
            }
        }

        Exemplifier.run();
    }
}
