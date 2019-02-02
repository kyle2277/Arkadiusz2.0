import java.util.*;
import java.io.*;
import org.ejml.simple.*;

public class ArkadiuszMain {
   
   public static EncoderDecoder e;
   public static AccountVault a;
   
   public static void main (String[] args) throws FileNotFoundException, IOException {
      File file = new File("dictionary.txt");
      Scanner dictionary = new Scanner(file);
      CharacterList list = new CharacterList();
      list.read(dictionary);
      a = new AccountVault();
      Scanner input = new Scanner(System.in);
      System.out.println("Enter encode key");
      String key = input.nextLine();
      e = new EncoderDecoder(key, list);
      String command = "";
      while (!command.equals("quit")) {
         command = listen(input);
      }
      
   }
      
   public static String listen(Scanner input) throws IOException {
      System.out.println("help for help");
      String command = input.nextLine();
      if (command.length() < 4) {
         System.out.println("Unrecognized command");
         return "run";
      }
      switch (command.substring(0,4)) {
         case "help":
            System.out.println();
            System.out.println("-add <account name> <username>\tadd a new account (write spaces as '-')");
            System.out.println("edit <account name> <username or password>\tchange credientials of an existing account");
            System.out.println("-del <account name>\tremove an existing account");
            System.out.println("show <account name>\tget existing credentials; No parameter for list of existing accounts");
            System.out.println("quit\texit program");
            System.out.println();
            break;
         case "-add":
            if (checkString(command)) {
               addError();
            } else {
               add(command.substring(5), input);
            }
            break;
         case "edit":
            if (checkString(command)) {
               editError();
            } else {
               edit(command.substring(5), input, e);
            }
            break;
         case "-del":
            if (checkString(command)) {
               delError();
            } else {
               del(command.substring(5), input);
            }
            break;
         case "show":
            if (command.substring(4).isEmpty()) {
               a.printAccnts();
            } else if (checkString(command)) {
               showError();
            } else {
               show(command.substring(5), input);
            }
            break;
         case "quit":
            return "quit";
         default:
            System.out.println("Unrecognized command");
            break;
      }
      return "run"; 
   }
   
   public static boolean checkString(String n) {
      return ((n.substring(4).isEmpty()) ||
               (n.substring(5).isEmpty()) ||
               (!n.substring(4,5).equals(" ") && !n.substring(5,6).isEmpty()));
   }
   
   public static void add(String command, Scanner input) throws IOException {
      System.out.println(command);
      String[] components = command.split(" ",0);
      if (components.length != 2) {
         addError();
      } else {
         System.out.println("Account name: " + components[0]);
         System.out.println("Username: " + components[1]);
         System.out.println("Please mind your surroundings and input password:");
         String password = input.next();
         Account newAccnt = new Account(components[0], components[1], password, e);
         System.out.println(newAccnt.getName());
         System.out.println(newAccnt.getUsername());
         System.out.println(newAccnt.getPassword());
         a.save(newAccnt);
         a = new AccountVault();
      }
   }
   
   public static void addError() {
      System.out.println("missing/inncorrect parameters.\n-add <account name> <username> (write spaces as '-')");
   }
   
   public static void edit(String command, Scanner input, EncoderDecoder e) throws IOException {
      String[] components = command.split(" ",0);
      if (a.edit(components, input, e)) {
         System.out.println(components[0] + " " + components[1] + " edit successful");
      } else {
         editError();
      }
   }
   
   public static void editError() {
      System.out.println("missing/incorrect parameters.\nedit <account name> <'username' or 'password'> (write spaces as '-')");
   }
   
   public static void del(String command, Scanner input) throws IOException {
      System.out.println(command);
      System.out.println("Delete " + command + "?");
      System.out.println("y - yes");
      System.out.println("any key - no");
      String confirm = input.nextLine();
      if (confirm.equals("y")) {
         if (a.delete(command)) {
            System.out.println("Account " + command + " has been deleted.");
         }
      } else {
         System.out.println("Deletion of " + command + " terminated.");
      }
   }
   
   public static void delError() {
      System.out.println("missing/incorrect parameters.\n-del <account name>");
   }
   
   public static void show(String command, Scanner input) {
      System.out.println(command);
      String[] credentials = a.fetch(command, e);
      if (!credentials[0].equals("Null")) {
         System.out.println("\nAccount: " +  credentials[0]);
         System.out.println("Username: " + credentials[1]);
         System.out.println("Password: " + credentials[2] + "\n");
      } else {
         System.out.println("Account of given name does not exist.");
      }
   }
   
   public static void showError() {
      System.out.println("missing/incorrect parameters.\nshow <account name>\nno parameter for list of existing accounts.");
   }
   
}