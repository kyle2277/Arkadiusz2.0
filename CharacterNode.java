import java.util.*;

public class CharacterNode{
   //primary character contained by the node
   public String primary;
   //secondary character contained by the node
   public String secondary;
   //number of the character
   public int index;

   //constructor given character and index
   public CharacterNode(String primary, String secondary, int index) {
      this.primary = primary;
      this.secondary = secondary;
      this.index = index;
   }
   
   public String toString() {
      return "Index: " + index + "\nPrimary character: " + primary + "\nSecondary character: " + secondary + "\n";
   }
   
}