import java.util.*;
import java.io.*;
import org.ejml.simple.*;
import org.apache.commons.lang3.*;

public class AccountVault {
   public static final String ACCOUNT_INDICATOR = "******";
   public static final String NEW_MAT_INDICATOR = "------";
   public static final String COLUMN_SEPARATOR = "_";
   public File vault_file;
   public ArrayList<Account> vault;

   public AccountVault() throws FileNotFoundException {
      vault = new ArrayList<Account>();
      this.vault_file = new File("vault.txt");
      if (vault_file.exists()) {
         Scanner sc = new Scanner(vault_file);
         buildLocalVault(sc);
         sc.close();
      }
   }
   
   public void buildLocalVault(Scanner sc) {
      while(sc.hasNextLine()) {
         if (sc.nextLine() == ACCOUNT_INDICATOR) {
            String name = sc.nextLine();
            Account curAccnt = new Account(name);
            parseMat(curAccnt, sc, true);
            sc.nextLine();
            parseMat(curAccnt, sc, false);
         }
      }
      
   }
   
   public void parseMat(Account curAccnt, Scanner sc, boolean username) {
      String first = sc.nextLine();
      int numCols = StringUtils.countMatches(first, COLUMN_SEPARATOR);
      Queue<Double> q = new LinkedList<Double>();
      for (int i = 0; i < numCols; i++) {
         
      }
      for (int i = 0; i < 4; i++) {
         
         for (int j = 0; j < numCols; j++) {
            
         }
      }
   }
   
   public void save(Account account) throws IOException {
      //boolean tells writer to append new data to file
      FileWriter wr = new FileWriter(vault_file,true);
      SimpleMatrix username = account.getUsername();
      SimpleMatrix password = account.getPassword();
      wr.write(ACCOUNT_INDICATAOR+"\n");
      wr.write(account.name+"\n");
      writeMat(wr, username);
      wr.write(NEW_MAT_INDICATOR+"\n");
      writeMat(wr, password);
      wr.close();
   }
   
   public void writeMat(FileWriter wr, SimpleMatrix mat) throws IOException {
      for (int i = 0; i < mat.numRows(); i++) {
         for (int j = 0; j < mat.numCols(); j++) {
            double val = mat.get(i,j);
            wr.write(val+COLUMN_SEPARATOR);
         }
         wr.write("\n");
      }
   }  
   
}