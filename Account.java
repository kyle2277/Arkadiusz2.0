import org.ejml.simple.*;
import java.util.*;

public class Account {
   
   //name of account
   String name;
   //encoded username
   SimpleMatrix username;
   //encoded password
   SimpleMatrix password;
   
   public Account(String name) {
      this.name = name;
   }
   
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