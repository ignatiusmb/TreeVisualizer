import java.util.ArrayList;
import java.util.List;

/**
 * Tree Printer with Diagram
 */
public class TreeVisualizer {
  /**
   * Implement on your Node class
   */
  public interface Visualized {
    /** Get left child */
    Visualized getLeft();

    /** Get right child */
    Visualized getRight();

    /** Get value of Node to be printed, could be String or Integer */
    String getValue();
  }


  /**
   * Prints the visualized tree
   *
   * @param root the root (top of the tree)
   */
  public static void print(Visualized root) {
    /** init line stack */
    List<List<String>> stack = new ArrayList<List<String>>();
    /** init level */
    List<Visualized> level = new ArrayList<Visualized>();
    /** init next level */
    List<Visualized> nextLevel = new ArrayList<Visualized>();

    /** adds the root to the first level */
    level.add(root);
    /** init next nodes from the root */
    int nextNodes = 1;
    /** init widest Value */
    int widest = 0;

    while (nextNodes != 0) {
      /** init current line */
      List<String> line = new ArrayList<String>();
      /** reset next nodes to 0 */
      nextNodes = 0;

      /** add all nodes in current level to current line */
      for (Visualized node : level) {
        if (node != null) {
          String value = node.getValue();
          line.add(value);
          if (value.length() > widest)
            widest = value.length();
          Visualized left = node.getLeft();
          Visualized right = node.getRight();
          nextLevel.add(left);
          nextLevel.add(right);
          if (left != null || right != null)
            nextNodes++;
        } else {
          line.add(null);
          nextLevel.add(null);
          nextLevel.add(null);
        }
      }
      /** if widest value is odd, increment by 1 to make even */
      if (widest % 2 == 1)
        widest++;
      /** add current line to the stack */
      stack.add(line);
      /** set current level to next level and clear the next level */
      List<Visualized> foo = level;
      level = nextLevel;
      nextLevel = foo;
      nextLevel.clear();
    }
    int stackSize = stack.size();
    /**
     * get width for each piece in each line by getting the length of the last element from the
     * first stack and multiplying it by the widest value in the stack to get each node's limit
     */
    int quota = stack.get(stackSize - 1).size() * (widest + 4);
    for (int i = 0; i < stackSize; i++) {
      /** get line from current iteration */
      List<String> line = stack.get(i);
      /** floor result of quota divided by 2 (-1) and convert result back to integer */
      int halfQuota = (int) Math.floor(quota / 2f) - 1;

      int lineSize = line.size();
      /** if current iteration is not the first line form the stack */
      if (i > 0) {
        /** prints the way from the line above to current line */
        for (int j = 0; j < lineSize; j++) {
          char split = ' ';
          /** split the node for values in line above */
          if (j % 2 == 1) {
            if (line.get(j - 1) != null) {
              split = (line.get(j) != null) ? '┴' : '┘';
            } else if (j < lineSize && line.get(j) != null) {
              split = '└';
            }
          }
          System.out.print(split);
          /** lines and spaces for before and after split node */
          if (line.get(j) == null) {
            /** print whitespace for empty value */
            for (int k = 0; k < quota - 1; k++) {
              System.out.print(" ");
            }
          } else {
            /** print first half of quota given */
            for (int k = 0; k < halfQuota; k++) {
              System.out.print(j % 2 == 0 ? " " : "─");
            }
            /** print middle part of value */
            System.out.print(j % 2 == 0 ? "┌" : "┐");
            /** print last half of quota given */
            for (int k = 0; k < halfQuota; k++) {
              System.out.print(j % 2 == 0 ? "─" : " ");
            }
          }
        }
        System.out.println();
      }
      /** print values */
      for (int j = 0; j < lineSize; j++) {
        String value = line.get(j);
        if (value == null)
          value = "";

        float gap = quota / 2f - value.length() / 2f;
        /** print a value */
        for (int k = 0; k < (int) Math.ceil(gap); k++)
          System.out.print(" ");
        System.out.print(value);
        for (int k = 0; k < (int) Math.floor(gap); k++)
          System.out.print(" ");
      }
      System.out.println();
      /** decrement quota for each value by division of 2 for next line */
      quota /= 2;
    }
  }
}
