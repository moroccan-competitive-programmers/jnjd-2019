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
    LazyMiller solver = new LazyMiller();
    int testCount = Integer.parseInt(in.next());
    for (int i = 1; i <= testCount; i++) {
      solver.solve(i, in, out);
    }
    out.close();
  }

  static class LazyMiller {

    int n;
    int m;
    int[] d;
    int[] a;
    int[] h;
    int[] diff;
    int[][] dp;

    int rec(int i, int m) {
      if (i >= n) {
        return m <= 0 ? 0 : Factories.INF;
      }
      if (m <= 0) {
        return 0;
      }
      if (dp[i][m] != -1) {
        return dp[i][m];
      }
      int ans = Factories.INF;
      // he won't have to retake
      ans = Math.min(ans, rec(i + 1, m - a[i]));
      // retake it
      ans = Math.min(ans, rec(i + 1, m - h[i]) + d[i]);
      return dp[i][m] = ans;
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
      n = in.nextInt();
      m = in.nextInt();
      d = in.nextIntArray(n);
      h = in.nextIntArray(n);
      a = in.nextIntArray(n);
      long allowed = ArrayUtils.sum(a);
      diff = new int[n];
      if (allowed >= m) {
        out.println("Test " + (testNumber) + ": " + 0);
      } else {
        dp = ArrayUtils.createMatrix(n + 1, m + 1, -1);
        out.println("Test " + (testNumber) + ": " + rec(0, m));
      }
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

    public int[] nextIntArray(int n) {
      int[] arr = new int[n];
      for (int i = 0; i < n; i++) {
        arr[i] = nextInt();
      }
      return arr;
    }

  }

  static class ArrayUtils {

    public static long sum(int[] arr) {
      long ret = 0;
      for (int i = 0; i < arr.length; i++) {
        ret += arr[i];
      }
      return ret;
    }

    public static int[][] createMatrix(int n, int m, int initialValue) {
      int[][] ret = new int[n][m];
      for (int[] a : ret) {
        Arrays.fill(a, initialValue);
      }
      return ret;
    }

  }

  static interface FastIO {

  }

  static final class Factories {

    public static int INF = 1 << 30;

    private Factories() {
    }

  }
}
