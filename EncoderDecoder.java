import java.util.*;
import org.ejml.simple.*;

public class EncoderDecoder {
   
   //master password
   public String encodeKey;
   //change of basis matrix
   public SimpleMatrix encryptionMatrix;
   //dictionary
   public CharacterList dictionary;
      
   public EncoderDecoder(String encodeKey, CharacterList dictionary) {
      this.dictionary = dictionary;
      this.encodeKey = encodeKey;
      generateMat(encodeKey);
   }
   
   //encode using "enctryption" change of basis matrix
   //return encrytped matrix
   public SimpleMatrix encode(String credential) {
      SimpleMatrix converted = generateConvertedMat(credential);
      SimpleMatrix encrypted = new SimpleMatrix(1,1);
      for (int i = 0; i < converted.numCols(); i++) {
         SimpleMatrix vector = converted.extractVector(false, i);
         SimpleMatrix result = encryptionMatrix.mult(vector);
         encrypted = encrypted.combine(0, i, result);
      }
      return encrypted;  
      
      
      //SimpleMatrix encoded = new SimpleMatrix();
   }
   
   public SimpleMatrix generateConvertedMat(String credential) {
      int length = credential.length();
      int quads = length/4;
      if (length % 4 != 0) {
         quads++;
      }
      double[][] conv = new double[4][quads];
      for (int i = 0; i <= quads - 1; i++) {
         for(int j = 0; j <= 3; j++) {
            if (!credential.isEmpty()) {
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
               double composite = (random*100)+node.index;
               conv[j][i] = composite;  
               credential = credential.substring(1);
            } else {
               conv[j][i] = 0;
            }
            
         }
      }
      SimpleMatrix converted = new SimpleMatrix(conv);
      System.out.println("Encoded (before scrambling) matrix:");
      converted.print();
      return converted;
   }
   
   public String decode(SimpleMatrix encoded) {
      String decoded="";
      SimpleMatrix decrypted = decodeMat(encoded);
      for (int i = 0; i < decrypted.numCols(); i++) {
         SimpleMatrix vector = decrypted.extractVector(false, i);
         for (int j = 0; j < vector.numRows(); j++) {
            String val = vector.get(j)+"";
            if (!val.equals("0.0")) {
               double d_index = Double.valueOf(val.substring(2));
               int index = (int) d_index;
               double d_indicator = Double.valueOf(val.substring(0,2));
               int indicator = (int) d_indicator;
               boolean primary = indicator < 20;
               if (primary) {
                  decoded += dictionary.getNode(index).primary;
               } else {
                  decoded += dictionary.getNode(index).secondary;
               }
            }
            
         }
      }
      System.out.println("Decrypted matrix:");
      decrypted.print();
      return decoded;
   }
   
   public SimpleMatrix decodeMat(SimpleMatrix encoded) {
      //decryption matrix = inverse of encryption matrix
      SimpleMatrix decryptionMat = encryptionMatrix.invert();
      SimpleMatrix decrypted = new SimpleMatrix(1,1);
      //make separate method for encoding and decoding
      for (int i = 0; i < encoded.numCols(); i++) {
         SimpleMatrix vector = encoded.extractVector(false, i);
         SimpleMatrix result = decryptionMat.mult(vector);
         decrypted = decrypted.combine(0, i, result);
      }
      return decrypted;
   }
   
   public void generateMat(String encodeKey) {
      /*
      generate change of basis matrix from given master password
      */
      double[][] a = new double[][] {
         {0, 2, 3, 4},
         {5, 6, 7, 8},
         {9, 11, 11, 12},
         {13, 14, 15, 16}
      };
      encryptionMatrix = new SimpleMatrix(a);
   }
   
   

}