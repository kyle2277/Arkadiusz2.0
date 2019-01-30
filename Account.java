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
   
   //consructs basic account, no username of password
   public Account(String name) {
      this.name = name;
   }
   
   //takes username and password, encodes with given encoder
   public Account(String name, String username, String password, Encoder e) {
      this.name = name;
      setUsername(username, e);
      setPassword(password, e);
   }
   
   
   public void setUsername(String username, Encoder e) {
      username = e.encode(username)
      
   }
   
   public void setPassword(String password, Encoder e) {
      password = e.encode(password);
   }
   
}