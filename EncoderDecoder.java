import java.util.*;
import org.ejml.simple.*;

public class EncoderDecoder {
   
   //master password
   private String encodeKey;
   //change of basis matrix
   private SimpleMatrix encryptionMatrix;
   //dictionary
   public CharacterList dictionary;
      
   public EncoderDecoder(String encodeKey, CharacterList dictionary) {
      this.dictionary = dictionary;
      this.encodeKey = encodeKey;
      generateMat(encodeKey);
   }
   
   //encode using enctryption matrix
   //return encrytped matrix
   public SimpleMatrix encode(String credential) {
      SimpleMatrix converted = generateConvertedMat(credential);
      
      //SimpleMatrix encoded = new SimpleMatrix();
      return null;
   }
   
   public SimpleMatrix generateConvertedMat(String credential) {
      int length = credential.length();
      int quads = length/4;
      if (length % 4 != 0) {
         quads++;
      }
      int[][] converted = new int[quads][4];
      for (int i = 0; i <= quads - 1; i++) {
         for(int j = 0; j <= 3; j++) {
            String letter = credential.substring(0,1);
            CharacterNode node = dictionary.getNode(letter);
            char c = letter.charAt(0);
            int random = (int)(Math.random()*10);
            if (letter.equals(node.primary)) {
               //if primary, first integer is random number between 10 and 20
               random = random+10;
            } else {
               //if secondary, first integer is random number between 20 and 30
               random = random+20;
            }
            int composite = (random*100)+node.index;
            converted[i][j] = composite;  
            credential = credential.substring(1);
         }
      }
      return converted;
   }
   
   public void generateMat(String encodeKey) {
      /*
      generate change of basis matrix from given master password
      */
   }
   
   

}