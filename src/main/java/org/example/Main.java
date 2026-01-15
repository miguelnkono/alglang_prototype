package org.example;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static boolean had_error = false;

    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("Synthax: alglang <scrip.al>");
            System.exit(0);
        }
        if (args.length == 1) {
            if (!args[0].endsWith(".al")) {
                System.err.println("Wrong script file!");
                System.exit(64);
            }
            if (!Files.exists(Path.of(args[0]))) {
                System.err.printf("File [%s] does not exist!\n", args[0]);
                System.exit(64);
            }

            // run the program
            run(args[0]);
        }
    }

    private static void run(String program) {}

    /**
     * This is the general function to report errors to the users.
     *
     * @param line    the line where the error occurred
     * @param message the message that we will report to the users to inform them that an error
     *                occurred
     *
     */
    private static void report(int line, String message) {
        report(line, null, message);
    }

    /**
     * This is the generale function to report errors to the users.
     *
     * @param line    the line where the error occurred
     * @param where   where the error was found in the source code
     * @param message the message that we will report to the users to inform them that an error
     *                occurred
     *
     */
    private static void report(int line, String where, String message) {
        System.err.format("[ligne %d ] Erreur : %s :  %s\n", line, where, message);
        Main.had_error = true;
    }

    /**
     * This function report errors the users without telling them where on what the error was
     * found.
     *
     * @param line    the line where the error occurred
     * @param message the message to display to the users
     *
     */
    public static void error(int line, String message) {
        Main.report(line, "", message);
    }

    public static void error(Token token, String message) {
        if (token.token_type() == TokenType.EOF) {
            report(token.token_line(), " à la fin", message);
        } else {
            report(token.token_line(), " à '" + token.token_lexeme() + "'", message);
        }
    }
}