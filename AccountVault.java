import java.util.*;
import java.io.*;
import org.ejml.simple.*;
import org.apache.commons.lang3.*;

//in-app container for accounts
public class AccountVault {
   //indicates new account in accounts txt
	public static final String ACCOUNT_INDICATOR = "******";
   //separates matrices in accounts txt
	public static final String NEW_MAT_INDICATOR = "------\n";
   //denotes spaces between columns in matrices in account txt
	public static final String COLUMN_SEPARATOR = "_";
   //txt that holds account matrices
	public File vault_file;
	//list of current accounts
   public ArrayList<Account> vault;
	
   public AccountVault() {
      try {
         vault = new ArrayList<Account>();
         this.vault_file = new File("vault.txt");
         Scanner sc = new Scanner(vault_file);
         buildLocalVault(sc);
         sc.close();
      } catch(FileNotFoundException e) {
         System.out.println("No vault.txt found");
         System.out.println("Program terminated.");
         System.exit(0);
      }
   }
   
	//constructs container of current accounts from account txt on local drive.
   public void buildLocalVault(Scanner sc) {
      while(sc.hasNextLine()) {
         String line = sc.nextLine();
         if (line.equals(ACCOUNT_INDICATOR)) {
            String name = sc.nextLine();
            Account curAccnt = new Account(name);
            SimpleMatrix username = parseMat(curAccnt, sc, true);
            curAccnt.setUsername(username);
            sc.nextLine();
            SimpleMatrix password = parseMat(curAccnt, sc, false);
            curAccnt.setPassword(password);
            vault.add(curAccnt);
         } else {
            sc.nextLine();
         }
      } 
   }
   
	/*
	imports matrices from accounts txt on drive into program.
	takes an account object, scanner and boolean of whether matrix in question is username or not (password).
	returns respective matrix (encrypted).
   */
	public SimpleMatrix parseMat(Account curAccnt, Scanner sc, boolean username) {
      String line = sc.nextLine();
      int numCols = StringUtils.countMatches(line, COLUMN_SEPARATOR);
		//Queue<Double> q = new LinkedList<Double>();
      double[][] extracted = new double[4][numCols];
      for (int i = 0; i < 4; i++) {
         String[] split = line.split("_",0);
         for (int j = 0; j < numCols; j++) {
            double num = Double.valueOf(split[j]);
            extracted[i][j] = num;
         }
         if (i < 3) {
            line = sc.nextLine();
         }
      }
      SimpleMatrix encrypted = new SimpleMatrix(extracted);
      //encrypted.print();
      return encrypted;
   }
   
	/*
	saves account to txt file on local drive.
	takes account.
   */
	public void save(Account account) throws IOException {
      //boolean tells writer to append new data to file
		FileWriter wr = new FileWriter(vault_file,true);
   	SimpleMatrix username = account.getUsername();
   	SimpleMatrix password = account.getPassword();
   	wr.write(ACCOUNT_INDICATOR);
   	wr.write("\n"+account.getName()+"\n");
   	writeMat(wr, username);
   	wr.write(NEW_MAT_INDICATOR);
   	writeMat(wr, password);
   	wr.close();
		System.out.println("IO Exception");
   }
   
	//transcribes encrypted matrix from program to accounts txt.
	//takes file writer and matrix to transcribe.
   public void writeMat(FileWriter wr, SimpleMatrix mat) throws IOException {
      for (int i = 0; i < mat.numRows(); i++) {
         for (int j = 0; j < mat.numCols(); j++) {
            double val = mat.get(i,j);
            wr.write(val+COLUMN_SEPARATOR);
         }
         wr.write("\n");
      }
   }
   
	/*
	gets username and password matrix of given account.
	takes name of account and encoder.
	returns list of account information.
   */
	public String[] fetch(String name, EncoderDecoder e) {
      String[] credentials = new String[3];
      Account accnt = exists(name);
      if (!accnt.getName().equals("Null")) {
         credentials[0] = accnt.getName();
         credentials[1] = accnt.decryptUsername(e);
         credentials[2] = accnt.decryptPassword(e);
         return credentials;
      } else {
         credentials[0] = "Null";
         return credentials;
      }
   }
   
	/*
	checks if account of given name exists in current container.
	returns account if exists, otherwise account named null.
   */
	public Account exists(String name) {
      for (Account accnt : vault) {
         if (accnt.getName().equalsIgnoreCase(name)) {
            return accnt;
         }
      }
      Account nullAccnt = new Account("Null");
      return nullAccnt;
   }
   
	/**
	remove account from current container of accounts.
	updates accounts file on local drive.
	returns true if account exists and deletion successful.
   */
	public boolean delete(String name) throws IOException {
      Account accnt = exists(name);
      if (!accnt.getName().equals("Null")) {
         vault.remove(accnt);
         vault_file.delete();
         //rebuild vault
         for (Account saveAccnts : vault) {
            save(saveAccnts);
         }
         return true;
      } else {
         return false;
      }
   }
	
	/*
	edit account of given name, updates account txt on local drive.
	takes list of account information, scanner and encoder.
	returns true if account exists and edit is successful.
	*/
   public boolean edit(String[] components, Scanner input, EncoderDecoder e) throws IOException {
      components[0] = components[0].replaceAll("-", " ");
      Account accnt = exists(components[0]);
      if ((components[1].equals("username")) || (components[1].equals("password")) && 
         (accnt.getName().equals(components[0]))) {
         System.out.println("Enter new " + components[1] + " for " + components[0] + ":");
         String credential = input.nextLine();
         if (components[1].equals("username")) {
            accnt.encryptUsername(credential, e);
         } else {
            accnt.encryptPassword(credential, e);
         }
         vault_file.delete();
         for (Account saveAccnts : vault) {
            save(saveAccnts);
         }
         return true;
      } else {
         return false;
      }
   }
   
	//prints all accounts in current container
   public void printAccnts() {
      System.out.println("Accounts on current file:");
      for (Account accnt : vault) {
         System.out.println("- "+ accnt.getName());
      }
   }

}