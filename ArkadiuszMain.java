import java.util.*;
import java.io.*;
import org.ejml.simple.*;

public class ArkadiuszMain {
   
   public static void main (String[] args) throws FileNotFoundException {
      File file = new File("dictionary.txt");
      Scanner dictionary = new Scanner(file);
      CharacterList list = new CharacterList();
      list.read(dictionary);
      AccountVault a = new AccountVault();
      Scanner input = new Scanner(System.in);
      System.out.println("Enter encode key");
      String key = input.nextLine();
      EncoderDecoder e = new EncoderDecoder(key, list);
      String command = "";
      while (!command.equals("quit")) {
         command = listen(input);
      }
      
   }
      
   public static String listen(Scanner input) {
      System.out.println("help for help");
      String command = input.nextLine();
      switch (command.substring(0,4)) {
         case "help":
            System.out.println();
            System.out.println("-add <account name> <username>\tadd a new account");
            System.out.println("edit <account name> <username or password>\tchange credientials of an existing account");
            System.out.println("-del <account name>\tremove an existing account");
            System.out.println("-get <account name>\tget existing credentials");
            System.out.println("quit\texit program");
            System.out.println();
            break;
         case "-add":
            if (command.substring(4).isEmpty()) {
               System.out.println("missing parameters.\n-add <account name> <username>");
            } else {
               add(command);
            }
            break;
         case "edit":
            if (command.substring(4).isEmpty()) {
               System.out.println("missing parameters.\nedit <account name> <'username' or 'password'>");
            } else {
               edit(command);
            }
            break;
         case "-del":
            if (command.substring(4).isEmpty()) {
               System.out.println("missing parameters.\n-del <account name>");
            } else {
               del(command);
            }
            break;
         case "-get":
            if (command.substring(4).isEmpty()) {
               System.out.println("missing parameters.\n-get <account name>");
            } else {
               get(command);
            }
            break;
         case "quit":
            return "quit";   
      }
      return "run"; 

   }
   
   public static void add(String command) {
      
   }
   
   public static void edit(String command) {
   
   }
   
   public static void del(String command) {
   
   }
   
   public static void get(String command) {
   
   }
   
}