import org.ejml.simple.*;
import java.util.*;

//Account node object
//holds name of account, encrypted username, password
public class Account {
   
   //name of account
   String name;
   //encoded username
   SimpleMatrix username;
   //encoded password
   SimpleMatrix password;
   //encoder
   EncoderDecoder e;
   
   //consructs basic account, no username of password
   public Account(String name) {
      this.name = name;
   }
   
   //takes username and password, encodes with given encoder
   public Account(String name, String username, String password, EncoderDecoder e) {
      this.name = name;
      this.e = e;
      setUsername(username);
      setPassword(password);
   }
   
   
   public void setUsername(String username) {
      this.username = e.encode(username);
      this.username.print(); 
   }
   
   public SimpleMatrix getUsername() {
      return username;
   }
   
   public void setPassword(String password) {
      this.password = e.encode(password);
   }
   
   public SimpleMatrix getPassword() {
      return password;
   }
   
}