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
   //encoder
   public EncoderDecoder e;
   
   //consructs basic account, no username of password
   public Account(String name) {
      this.name = name;
   }
   
   //takes username and password, encodes with given encoder
   public Account(String name, String username, String password, EncoderDecoder e) {
      this.name = name;
      this.e = e;
      encryptUsername(username);
      encryptPassword(password);
   }
   
   
   public void encryptUsername(String username) {
      this.username = e.encode(username);
      this.username.print(); 
   }
   
   public void setUsername(SimpleMatrix username) {
      this.username = username;
   }
   
   public SimpleMatrix getUsername() {
      return username;
   }
   
   public void encryptPassword(String password) {
      this.password = e.encode(password);
   }
   
   public void setPassword(SimpleMatrix password) {
      this.password = password;
   }
   
   public SimpleMatrix getPassword() {
      return password;
   }
   
}