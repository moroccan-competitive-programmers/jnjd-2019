import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;
import java.util.Comparator;
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
    HoudaAndArrayManipulation solver = new HoudaAndArrayManipulation();
    solver.solve(1, in, out);
    out.close();
  }

  static class HoudaAndArrayManipulation {

    long[] arr;
    List<LongPair>[] buckets;
    int bucketSize;
    int n;
    int m;

    int query(int l, int r, long v) {
      int ans = 0;
      for (int i = l; i <= r; ++i) {
        if (i % bucketSize == 0 && i + bucketSize - 1 <= r) {
          ans += getForBucket(i / bucketSize, v);
          i += bucketSize - 1;
        } else {
          if (arr[i] < v) {
            ++ans;
          }
        }
      }
      return ans;
    }

    void update(int index, long v) {
      arr[index] = v;
      int buck = index / bucketSize;
      for (int i = 0; i < buckets[buck].size(); ++i) {
        if (buckets[buck].get(i).second == index) {
          buckets[buck].get(i).setFirst(v);
          break;
        }
      }
      buckets[buck].sort(Comparator.comparingLong(e -> e.first));
    }

    private int getForBucket(int i, long v) {
      int lo = 0, hi = buckets[i].size() - 1;
      int ret = -1;
      while (lo <= hi) {
        int mid = (lo + hi) >> 1;
        if (buckets[i].get(mid).first < v) {
          ret = mid;
          lo = mid + 1;
        } else {
          hi = mid - 1;
        }
      }
      return ret + 1;
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
      n = in.nextInt();
      m = in.nextInt();
      bucketSize = (int) (Math.sqrt(n) + 1);
      buckets = new ArrayList[bucketSize + 1];
      for (int i = 0; i <= bucketSize; ++i) {
        buckets[i] = new ArrayList<>();
      }
      arr = in.nextLongArray(n);
      for (int i = 0; i < n; ++i) {
        buckets[i / bucketSize].add(Factories.makeLongPair(arr[i], i));
      }
      for (int i = 0; i <= bucketSize; ++i) {
        buckets[i].sort(Comparator.comparingLong(val -> val.first));
      }
      while (m-- > 0) {
        int type = in.nextInt();
        if (type == 1) {
          int l = in.nextInt() - 1;
          int r = in.nextInt() - 1;
          long v = in.nextLong();
          out.println(query(l, r, v));
        } else {
          int index = in.nextInt() - 1;
          long val = in.nextLong();
          update(index, val);
        }
      }
    }

  }

  static class LongPair implements Comparable<LongPair> {

    public long first;
    public long second;

    public LongPair(long first, long second) {
      this.first = first;
      this.second = second;
    }

    public int compareTo(LongPair a) {
      if (second == a.second) {
        return Long.compare(first, a.first);
      }
      return Long.compare(second, a.second);
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

      LongPair longPair = (LongPair) o;

      if (first != longPair.first) {
        return false;
      }
      return second == longPair.second;
    }

    public int hashCode() {
      long result = (int) (first ^ (first >>> 32));
      result = 31L * result + (second ^ (second >>> 32));
      return (int) (result >>> 10);
    }

    public void setFirst(long first) {
      this.first = first;
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

    public long nextLong() {
      int c;
      for (c = this.read(); isSpaceChar(c); c = this.read()) {
      }

      byte sgn = 1;
      if (c == 45) {
        sgn = -1;
        c = this.read();
      }

      long res = 0;

      while (c >= 48 && c <= 57) {
        res *= 10L;
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

    public long[] nextLongArray(int n) {
      long[] arr = new long[n];
      for (int i = 0; i < n; i++) {
        arr[i] = nextLong();
      }
      return arr;
    }

  }

  static final class Factories {

    private Factories() {
    }

    public static LongPair makeLongPair(long first, long second) {
      return new LongPair(first, second);
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
}
