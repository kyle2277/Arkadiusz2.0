import org.ejml.simple.*;
import java.util.*;

//Account node object
//holds name of account, encrypted username, password
public class Account {
   
   //name of account
   public String name;
   //encoded username
   public SimpleMatrix username;
   //encoded password
   public SimpleMatrix password;
      
   //consructs basic account, no username of password
   public Account(String name) {
      this.name = name;
   }
   
   //takes username and password, encodes with given encoder
   public Account(String name, String username, String password, EncoderDecoder e) {
      this.name = name.replaceAll(" ", "-");
      encryptUsername(username, e);
      encryptPassword(password, e);
   }
   
   public String getName() {
      return name;
   }
   
   public void encryptUsername(String username, EncoderDecoder e) {
      setUsername(e.encode(username));
      //this.username.print(); 
   }
   
   public String decryptUsername(EncoderDecoder e) {
      return e.decode(this.username);
   }
   
   //used when creating local list of accounts
   public void setUsername(SimpleMatrix username) {
      this.username = username;
   }
   
   public SimpleMatrix getUsername() {
      return username;
   }
   
   public void encryptPassword(String password, EncoderDecoder e) {
      setPassword(e.encode(password));
   }
   
   public String decryptPassword(EncoderDecoder e) {
      return e.decode(this.password);
   }
   
   //used when creating local list of accounts
   public void setPassword(SimpleMatrix password) {
      this.password = password;
   }
   
   public SimpleMatrix getPassword() {
      return password;
   }
   
}