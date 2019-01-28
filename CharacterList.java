import java.util.*;

public class CharacterList {
   
   //directory of characters
   ArrayList<CharacterNode> dictionary;
   
   public CharacterList() {
      dictionary = new ArrayList<CharacterNode>();
   }
   
   public void read(Scanner input) {
      int itr = 0;
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
   
   public CharacterNode getNode(int index) {
      return dictionary.get(index);
   }
   
   public CharacterNode getNode(String character) {
      for (CharacterNode node : dictionary) {
         if ((character.equals(node.primary)) || (character.equals(node.secondary))) {
            return node;
         }  
      }
      return null;
   }
   
   public void print() {
      for (CharacterNode node : dictionary) {
         System.out.println(node.toString());
      }
   }
   
}