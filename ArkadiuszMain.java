import java.util.*;
import java.io.*;
import org.ejml.simple.*;
import org.apache.commons.lang3.*;

/*
Arkadiusz2.0 is a password protection program designed for quick and secure encyrption
of usernames and passwords. This program internally organizes accounts and
their respective credientials, making retrieving account information easy and efficient.
Credentials are stored solely in the form of encrypted matrices in a local file called vault.txt
*/
public class ArkadiuszMain {
   
	//encoder
   public static EncoderDecoder e;
   //container of current accounts
	public static AccountVault a;
   
	/*
	throws exception if dicionary file not found in current directory
	*/
   public static void main (String[] args) {
      try {
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
			splash();
			//System.out.println("Welcome to Arkadiusz2.0 Password Encryption and Storage");
      	while (!command.equals("quit")) {
         	command = listen(input);
      	}
		} catch (IOException e) {
			System.out.println("Dicionary not found\nProgram terminated.");
		}
   }
	
	//splash ascii art
	public static void splash() {
		System.out.println("	                                             |>>>");
		System.out.println("                                                |");
		System.out.println("                                            _  _|_  _");
		System.out.println("                                           |;|_|;|_|;|");
		System.out.println("                                           \\\\.    .  /");
		System.out.println("            | ARKADIUSZ 2.0    |            \\\\:  .  /");
		System.out.println("            |                  |             ||:   |");
		System.out.println("            |                  |             ||:.  |");
		System.out.println("            |  Password        |             ||:  .|");
		System.out.println("            |  Potection       |             ||:   |       \\,/");
		System.out.println("            |  and Encryption  |             ||: , |            /`\\");
		System.out.println("                                             ||:   |");
		System.out.println("                                             ||: . |");
		System.out.println("              __                            _||_   |");
		System.out.println("     ____--`~    '--~~__            __ ----~    ~`---,              ___");
		System.out.println("-~--~                   ~---__ ,--~'                  ~~----_____-~'   `~----~~");
		System.out.println();
		
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
   
	//checks if user input has options/is formatted correctly
   public static boolean checkString(String n) {
      return ((n.substring(4).isEmpty()) ||
               (n.substring(5).isEmpty()) ||
               (!n.substring(4,5).equals(" ") && !n.substring(5,6).isEmpty()));
   }
   
	//add a new account to the current container and local account file
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
   
	//tiggered if add command incomplete/used incorrectly
   public static void addError() {
      System.out.println("missing/inncorrect parameters.\n-add <account name> <username> (write spaces as '-')");
   }
   
	//edit an account in current container
   public static void edit(String command, Scanner input, EncoderDecoder e) throws IOException {
      String[] components = command.split(" ",0);
      if (a.edit(components, input, e)) {
         System.out.println(components[0] + " " + components[1] + " edit successful");
      } else {
         editError();
      }
   }
   
	//triggered if edit command incomplete/used incorrectly
   public static void editError() {
      System.out.println("missing/incorrect parameters.\nedit <account name> <'username' or 'password'> (write spaces as '-')");
   }
   
	//delete an account in current container
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
   
	//triggered if delete command incomplete/used incorrectly
   public static void delError() {
      System.out.println("missing/incorrect parameters.\n-del <account name>");
   }
   
	//show all accounts in currently available in container or show specific account of given name
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
   
	//triggered if show commmand incomplete/used incorrectly
   public static void showError() {
      System.out.println("missing/incorrect parameters.\nshow <account name>\nno parameter for list of existing accounts.");
   }
   
}