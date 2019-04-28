import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int T = in.nextInt();
    while (T-- > 0) {
      int n = in.nextInt();
      HashSet<Integer> set = new HashSet<>();
      for (int i = 0; i < n; ++i)
        set.add(in.nextInt());

      System.out.println(Math.max(0, set.size() - 2));
    }
  }
}
