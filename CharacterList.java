import java.util.*;

//list of organized characters in dictionary
//used to deconstruct and reconstruct credentials
public class CharacterList {
   
   //directory of characters
   public ArrayList<CharacterNode> dictionary;
   
   public CharacterList() {
      dictionary = new ArrayList<CharacterNode>();
   }
   
   //adds character to the dictionary
   public void read(Scanner input) {
      int itr = 1;
      while(input.hasNextLine()) {
         int index = itr;
         String next = input.nextLine();
         String primary = next.substring(0,1);
         String secondary = next.substring(1);
         CharacterNode node = new CharacterNode(primary, secondary, index);
         dictionary.add(node);
         itr++; 
      }  
   }
   
   //gets the node of given index
   public CharacterNode getNode(int index) {
      if ((index > dictionary.size()) || (index <= 0)) {
         int randInd = (int)(dictionary.size() - (dictionary.size()*Math.random()));
         System.out.println(randInd);
         return dictionary.get(randInd);
      }
      return dictionary.get(index-1);
   }
   
   //gets the node of given character
   public CharacterNode getNode(String character) {
      for (CharacterNode node : dictionary) {
         if ((character.equals(node.primary)) || (character.equals(node.secondary))) {
            return node;
         }  
      }
      return null;
   }
   
   //prints entire dictionary
   public void print() {
      for (CharacterNode node : dictionary) {
         System.out.println(node.toString());
      }
   }
   
}