import org.ejml.simple.*;
import java.util.*;
import java.io.*;

public class CharacterTesting {

   public static void main(String[] args) throws FileNotFoundException {
      File file = new File("dictionary.txt");
      Scanner dictionary = new Scanner(file);
      CharacterList list = new CharacterList();
      list.read(dictionary);
      //list.print();
      Random rand = new Random();
      for (int i = 0; i < 100; i++) {
         System.out.println((int)(Math.random()*10)+10);
      }
   }

}

  