package org.example;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
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
           System.out.println("cool");
       }
    }
}