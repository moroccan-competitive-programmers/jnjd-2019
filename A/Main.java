import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

/**
 * Created on 4/23/2019
 *
 * @author mcherach020419
 */
public class Main {

  public static void main(String[] args) {
    InputStream is = System.in;
    OutputStream os = System.out;
    InputReader in = new InputReader(is);
    PrintWriter out = new PrintWriter(new BufferedOutputStream(os, 1 << 18));
    Solver solver = new Solver();
    solver.solve(in, out);
    out.close();
  }

  static class Solver {

    int n, m, q;
    int[] size, rank, parent;

    void init() {
      size = new int[n + 1];
      rank = new int[n + 1];
      parent = new int[n + 1];
      for (int i = 0; i <= n; ++i) {
        size[i] = rank[i] = 1;
        parent[i] = i;
      }
    }

    int find(int root) {
      return parent[root] == root ? root : (parent[root] = find(parent[root]));
    }

    void merge(int u, int v) {
      if ((u = find(u)) == (v = find(v))) {
        return;
      }

      if (rank[u] < rank[v]) {
        int temp = u;
        u = v;
        v = temp;
      }
      rank[u] += rank[v];
      parent[v] = u;
    }

    int size(int u) {
      return rank[find(u)];
    }

    void solve(InputReader in, PrintWriter out) {
      n = in.nextInt();
      m = in.nextInt();
      q = in.nextInt();

      init();

      for (int i = 0; i < m; ++i) {
        int u = in.nextInt();
        int v = in.nextInt();
        merge(u, v);
      }

      while (q-- > 0) {
        int u = in.nextInt();
        int v = in.nextInt();
        merge(u, v);
        out.println(n - size(1));
      }
    }
  }

  static class InputReader {

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

    public static boolean isSpaceChar(int c) {
      return c == 32 || c == 10 || c == 13 || c == 9 || c == EOF;
    }

  }
}
