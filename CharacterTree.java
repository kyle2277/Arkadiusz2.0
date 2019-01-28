import java.util.*;

public class CharacterTree{

   public CharacterNode overallRoot;
   public Scanner console;
   public ArrayList<CharacterNode> list;
   
   public CharacterTree() {
      console = new Scanner(System.in);
      overallRoot = new CharacterNode(null, 0);
      list = new ArrayList<CharacterNode>();
   }
   
   public void read(Scanner dictionary) {
      while(dictionary.hasNextLine()) {
         String next = dictionary.nextLine();
         int index = Integer.parseInt(next.substring(0,1));
         String character = next.substring(1);
         CharacterNode cur = new CharacterNode(character, index);
         list.add(cur);
      }
      int middle = getMiddle(list);
      overallRoot = list.get(middle);
      overallRoot.setList(list);
   }
   
   public int getMiddle(ArrayList<CharacterNode> list) {
      int middle;
      if((list.size()%2) == 1) { /*will be evenly split*/
         middle = (list.size()+1)/2;
      } else {
         middle = (list.size()/2);
      }
      return middle;
   }
   
   public void buildTree() {
       overallRoot = buildTree(overallRoot);
   }
   
   private CharacterNode buildTree(CharacterNode root) {
      ArrayList<CharacterNode> left = new ArrayList<CharacterNode>();
      ArrayList<CharacterNode> right = new ArrayList<CharacterNode>();
      ArrayList<CharacterNode> thisList = root.list;
      if (thisList.size() > 2) {
         int middle = getMiddle(thisList);
         for (int i = 0; i <= middle-2; i++) {
            //add to left and right lists
            left.add(thisList.get(i));
         }
         if (thisList.size()%2==1) {
            //even
            for (int j = 0; j <= middle-2; j++) {
               right.add(thisList.get(middle+j));
            }
         } else {
            //odd
            for (int k = 0; k <= middle-1; k++) {
               right.add(thisList.get(middle+k));
            }
            
         }
         
         CharacterNode leftNode = left.get(getMiddle(left));
         leftNode.setList(left);
         System.out.println(getMiddle(left));
         
         CharacterNode rightNode = right.get(getMiddle(right));
         rightNode.setList(right);
         System.out.println(getMiddle(right));
         
         root.left = buildTree(leftNode);
         root.right = buildTree(rightNode);
      } else {
         CharacterNode last = thisList.get(0);
         
         if (last.index < root.index) {
            root.left = last;
         } else {
            root.right = last;
         }
         
      }
      return root;
   }
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      /*
         ArrayList<CharacterNode> left = new ArrayList<CharacterNode>();
         ArrayList<CharacterNode> right = new ArrayList<CharacterNode>();
         int middle;
         if((curList.size()%2) == 1) {
            middle = ((curList.size()+1)/2)-1;
            for (int i = 0; i <= middle-1; i++) {
               left.add(curList.get(i));
            }
         } else {
            middle = curList.size()/2-1;
            for (int j = 0; j <= middle-1; j++) {
               left.add(curList.get(j));
            }
         }
         CharacterNode root = curList.get(middle);
         root.left = buildTree(left, root);
         
         if((curList.size()%2) == 1) {
            middle = ((curList.size()+1)/2)-1;
            for (int i = 0; i <= middle-1; i++) {
               right.add(curList.get(middle + i));
            }
         } else {
            middle = curList.size()/2-1;
            for (int k = 0; k <= middle; k++) {
               right.add(curList.get(middle + k));
            }
         }

         
         root.right = buildTree(right, root);
         return root;
      }
      return overallRoot;
   
   }*/
   
   public void printList() {
      for (int i = 0; i < list.size(); i++) {
         System.out.println(list.get(i).character);
      }
   }
   
}