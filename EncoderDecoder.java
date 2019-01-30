import java.util.*;
import org.ejml.simple.*;

//Core encoder and decoder
//requires and encoder key to be accessed
public class EncoderDecoder {
   
   //master password
   public String encodeKey;
   //change of basis matrix
   public SimpleMatrix encryptionMatrix;
   //dictionary
   public CharacterList dictionary;
   
   //constructs encoder object, takes master password and dictionary   
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
   
   //takes credential to convert and turns string values into a matrix to be encrypted
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
      //System.out.println("Encoded (before scrambling) matrix:");
      //converted.print();
      return converted;
   }
   
   //takes an encoded matrix and converts it back to a string
   public String decode(SimpleMatrix encoded) {
      String decoded="";
      SimpleMatrix decrypted = decodeMat(encoded);
      for (int i = 0; i < decrypted.numCols(); i++) {
         SimpleMatrix vector = decrypted.extractVector(false, i);
         for (int j = 0; j < vector.numRows(); j++) {
            String val = vector.get(j)+"";
            if (!val.equals("0.0")) {
               double d_index = Double.valueOf(val.substring(2));
               int index = (int) Math.round(d_index);
               //makes sure characters that become close to zero after decryption 
               //are read as not characters
               if (index > (1.e-4)) {
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
      }
      //System.out.println("Decrypted matrix:");
      //decrypted.print();
      return decoded;
   }
   
   //takes encoded matrix and applies change of basis to un-encrypt
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
   
   //generates unique change of basis matrix from given master password
   public void generateMat(String encodeKey) {
      double sum1 = 0;
      double sum2 = 0;
      for (int i = 0; i < encodeKey.length(); i++) {
         sum1 += encodeKey.charAt(i);
         sum2 += (encodeKey.charAt(i))*(Math.log(encodeKey.charAt(i)));
      }
      double nums = Math.log(sum1);
      double nums2 = Math.log(sum2);
      String numStr = nums+"";
      String numStr2 = nums2+"";
      numStr= numStr.replaceAll("[.]", "");
      numStr2 = numStr2.replaceAll("[.]", "");
      ArrayList<Double> contain = new ArrayList<Double>();
      for (int j = 0; j < numStr.length(); j++) {
         String str1 = numStr.substring(j,j+1);
         String str2 = numStr2.substring(j,j+1);
         String union = str1+str2;
         double union_double = (double) Integer.parseInt(union);
         contain.add(union_double);
      } 
      double[][] matArray = populateMat(contain);
      SimpleMatrix gen = new SimpleMatrix(matArray);
      if (gen.determinant() == 0) {
         System.out.println("Invalid password");
      } else {
         encryptionMatrix = gen;
         //encryptionMatrix.print();
      }
   }
   
   //takes values from string representation of encryption code and puts into 
   //the change of basis matrix
   public double[][] populateMat(ArrayList<Double> contain) {
      double[][] matArray = new double[4][4];
      int count = 0;
      for (int k = 0; k < 4; k++) {
         for (int w = 0; w < 4; w++) {
            matArray[k][w] = contain.get(count);
            count++;
         }
      }
      return matArray;
   }
   
   

}