import org.ejml.simple.*;
import java.util.*;
import java.io.*;

public class CharacterTesting {

   public static void main(String[] args) throws FileNotFoundException, IOException {
      File file = new File("dictionary.txt");
      Scanner dictionary = new Scanner(file);
      CharacterList list = new CharacterList();
      list.read(dictionary);
      EncoderDecoder e = new EncoderDecoder("words", list);
      AccountVault a = new AccountVault();
      Account test = new Account("testing", "username", "password", e);
      a.save(test);
      Account test2 = new Account("testing also", "aaaaaaa", "hhhhhhhh", e);
      a.save(test2);
      
      
      /*
      System.out.println("Encryption (change of basis) matrix:");
      e.encryptionMatrix.print();
      SimpleMatrix A = e.encode("kylejwon@gmail.com");
      System.out.println("Encrypted matrix:");
      A.print();
      System.out.println(e.decode(A));
      */    
      //list.print();
      //Random rand = new Random();
      //for (int i = 0; i < 100; i++) {
        // System.out.println((int)(Math.random()*10)+10);
      //}
      
      /*
      SimpleMatrix A = new SimpleMatrix(array);
      A.print();
      SimpleMatrix B = A.extractVector(false, 0);
      B.print();
      SimpleMatrix C = A.mult(B);
      C.print();
      SimpleMatrix Ainv = A.invert();
      Ainv.print();
      SimpleMatrix result = Ainv.mult(C);
      result.print();
      */
      
      
      //System.out.println(A.determinant());
   }

}

  