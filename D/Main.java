import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in Actual solution is at the top
 *
 * @author MaxHeap
 */
public class Main {

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    OutputWriter out = new OutputWriter(outputStream);
    IbrahimAndTreeQuestions solver = new IbrahimAndTreeQuestions();
    int testCount = Integer.parseInt(in.next());
    for (int i = 1; i <= testCount; i++) {
      solver.solve(i, in, out);
    }
    out.close();
  }

  static class IbrahimAndTreeQuestions {

    List<IntPair>[] g;
    int[][] min;
    int[][] max;
    int[][] sparse;
    int[] depth;
    int n;
    int q;

    void dfs(int u, int p) {
      sparse[0][u] = p;
      for (IntPair e : g[u]) {
        int v = e.first;
        int edgeIndex = e.second;
        if (v != p) {
          depth[v] = depth[u] + 1;
          max[0][v] = edgeIndex;
          min[0][v] = edgeIndex;
          dfs(v, u);
        }
      }
    }

    void build() {
      for (int i = 1; i < 22; ++i) {
        for (int j = 1; j <= n; ++j) {
          if (sparse[i - 1][j] != -1) {
            sparse[i][j] = sparse[i - 1][sparse[i - 1][j]];
            max[i][j] = Math.max(max[i - 1][j], max[i - 1][sparse[i - 1][j]]);
            min[i][j] = Math.min(min[i - 1][j], min[i - 1][sparse[i - 1][j]]);
          }
        }
      }
    }

    int getMinimum(int u, int v) {
      if (depth[u] < depth[v]) {
        int temp = u;
        u = v;
        v = temp;
      }
      int best = n;
      for (int i = 21; i >= 0; --i) {
        if ((depth[u] - (1 << i)) >= depth[v]) {
          best = Math.min(best, min[i][u]);
          u = sparse[i][u];
        }
      }
      if (u == v) {
        return best;
      }

      for (int i = 21; i >= 0; --i) {
        if (sparse[i][u] != sparse[i][v]) {
          best = Math.min(min[i][u], best);
          best = Math.min(min[i][v], best);
          u = sparse[i][u];
          v = sparse[i][v];
        }
      }
      return Math.min(Math.min(best, min[0][u]), Math.min(best, min[0][v]));
    }

    int getMaximum(int u, int v) {
      if (depth[u] < depth[v]) {
        int temp = u;
        u = v;
        v = temp;
      }
      int best = -1;
      for (int i = 21; i >= 0; --i) {
        if ((depth[u] - (1 << i)) >= depth[v]) {
          best = Math.max(best, max[i][u]);
          u = sparse[i][u];
        }
      }
      if (u == v) {
        return best;
      }

      for (int i = 21; i >= 0; --i) {
        if (sparse[i][u] != sparse[i][v]) {
          best = Math.max(max[i][u], best);
          best = Math.max(max[i][v], best);
          u = sparse[i][u];
          v = sparse[i][v];
        }
      }
      return Math.max(Math.max(best, max[0][u]), Math.max(best, max[0][v]));
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
      n = in.nextInt();
      q = in.nextInt();
      g = new ArrayList[n + 1];
      for (int i = 0; i <= n; ++i) {
        g[i] = new ArrayList<>();
      }
      min = new int[22][n + 1];
      max = new int[22][n + 1];
      depth = new int[n + 1];
      sparse = ArrayUtils.createMatrix(22, n + 1, -1);
      for (int i = 1; i < n; ++i) {
        int u = in.nextInt();
        int v = in.nextInt();
        g[u].add(Factories.makeIntPair(v, i));
        g[v].add(Factories.makeIntPair(u, i));
      }
      dfs(1, 0);
      build();
      while (q-- > 0) {
        int l = in.nextInt();
        int r = in.nextInt();
        int u = in.nextInt();
        int v = in.nextInt();
        if (u == v) {
          out.println("YES");
        } else {
          int min = getMinimum(u, v);
          int max = getMaximum(u, v);
          if (min >= l && max <= r) {
            out.println("YES");
          } else {
            out.println("NO");
          }
        }
      }
    }

  }

  static class InputReader implements FastIO {

    private InputStream stream;
    private static final int DEFAULT_BUFFER_SIZE = 1 << 16;
    private static final int EOF = -1;
    private byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
    private int curChar;
    private int numChars;

    public InputReader(InputStream stream) {
      this.stream = stream;
    }

    public int read() {
      if (this.numChars == EOF) {
        throw new UnknownError();
      } else {
        if (this.curChar >= this.numChars) {
          this.curChar = 0;

          try {
            this.numChars = this.stream.read(this.buf);
          } catch (IOException ex) {
            throw new InputMismatchException();
          }

          if (this.numChars <= 0) {
            return EOF;
          }
        }

        return this.buf[this.curChar++];
      }
    }

    public int nextInt() {
      int c;
      for (c = this.read(); isSpaceChar(c); c = this.read()) {
      }

      byte sgn = 1;
      if (c == 45) {
        sgn = -1;
        c = this.read();
      }

      int res = 0;

      while (c >= 48 && c <= 57) {
        res *= 10;
        res += c - 48;
        c = this.read();
        if (isSpaceChar(c)) {
          return res * sgn;
        }
      }

      throw new InputMismatchException();
    }

    public String next() {
      int c;
      while (isSpaceChar(c = this.read())) {
      }

      StringBuilder result = new StringBuilder();
      result.appendCodePoint(c);

      while (!isSpaceChar(c = this.read())) {
        result.appendCodePoint(c);
      }

      return result.toString();
    }

    public static boolean isSpaceChar(int c) {
      return c == 32 || c == 10 || c == 13 || c == 9 || c == EOF;
    }

  }

  static final class Factories {

    private Factories() {
    }

    public static IntPair makeIntPair(int first, int second) {
      return new IntPair(first, second);
    }

  }

  static class ArrayUtils {

    public static int[][] createMatrix(int n, int m, int initialValue) {
      int[][] ret = new int[n][m];
      for (int[] a : ret) {
        Arrays.fill(a, initialValue);
      }
      return ret;
    }

  }

  static class OutputWriter extends PrintWriter {

    public OutputWriter(OutputStream os, boolean autoFlush) {
      super(os, autoFlush);
    }

    public OutputWriter(Writer out) {
      super(out);
    }

    public OutputWriter(Writer out, boolean autoFlush) {
      super(out, autoFlush);
    }

    public OutputWriter(String fileName) throws FileNotFoundException {
      super(fileName);
    }

    public OutputWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
      super(fileName, csn);
    }

    public OutputWriter(File file) throws FileNotFoundException {
      super(file);
    }

    public OutputWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
      super(file, csn);
    }

    public OutputWriter(OutputStream out) {
      super(out);
    }

    public void flush() {
      super.flush();
    }

    public void close() {
      super.close();
    }

  }

  static interface FastIO {

  }

  static class IntPair implements Comparable<IntPair> {

    public int first;
    public int second;

    public IntPair() {
      first = second = 0;
    }

    public IntPair(int first, int second) {
      this.first = first;
      this.second = second;
    }

    public int compareTo(IntPair a) {
      if (first == a.first) {
        return Integer.compare(second, a.second);
      }
      return Integer.compare(first, a.first);
    }

    public String toString() {
      return "<" + first + ", " + second + ">";
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      IntPair a = (IntPair) o;

      if (first != a.first) {
        return false;
      }
      return second == a.second;
    }

    public int hashCode() {
      int result = first;
      result = 31 * result + second;
      return result;
    }

  }
}
