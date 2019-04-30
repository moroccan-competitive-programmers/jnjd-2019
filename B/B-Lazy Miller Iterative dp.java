import java.util.*;
import java.io.*;
import java.math.*;
 
public class Main {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        //Scanner sc = new Scanner();
        Reader in = new Reader();
        Main solver = new Main();
        solver.solve(out, in);
        out.flush();
        out.close();
 
    }
 
    static int INF = (int)1e9;
    static int maxn = (int)1e4+5;
    static long mod=(int)1e9+7 ;
    static int n,need,k,t,q,x;
    
    static int[] d,h,a;
    
    
    //THE SOLUTION IS HERE
    void solve(PrintWriter out, Reader in) throws IOException{   
        t = in.nextInt();
        
        for(int idx=1;idx<=t;idx++){
            n = in.nextInt();
            need = in.nextInt();
            
            int sumofallowed=0;
            d = new int[n];
            h = new int[n];
            a = new int[n];
            for(int i=0;i<n;i++) d[i] = in.nextInt();
            for(int i=0;i<n;i++) h[i] = in.nextInt();
            for(int i=0;i<n;i++) {a[i] = in.nextInt();sumofallowed+=a[i];}
            
            //i removed a[i] from h[i] ,because i already computed the sum of a[i] (sumofallowed).
            for(int i=0;i<n;i++) h[i] -= a[i];
            
            int[][] dp = new int[n][2501];
            
            for(int i=0;i<n;i++)
                for(int j=1;j<2501;j++)
                    dp[i][j] = INF;
                    
            dp[0][sumofallowed] = 0;
            if(h[0]!=0) dp[0][sumofallowed+h[0]] = d[0];
            
            for(int i=1;i<n;i++)
                for(int j=i-1;j>=0;j--)
                    for(int c=h[i];c<2501;c++)
                        dp[i][c] = Math.min(dp[i][c],dp[j][c-h[i]]+d[i]);
            
            int ans = INF;
            for(int i=need;i<2501;i++)
                for(int j=0;j<n;j++)
                    ans = Math.min(ans,dp[j][i]);
                    
            out.println("Test "+idx+": "+ans);
            
        }
    }
    
    //<>
    
    static class Reader {

    private InputStream mIs;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;

    public Reader() {
        this(System.in);
    }

    public Reader(InputStream is) {
        mIs = is;
    }

    public int read() {
        if (numChars == -1) {
            throw new InputMismatchException();

    }
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = mIs.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0) {
                return -1;
            }
        }
        return buf[curChar++];
    }

    public String nextLine() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isEndOfLine(c));
        return res.toString();
    }

    public String next() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }

    double nextDouble()
    {
        return Double.parseDouble(next());
    }

    public long nextLong() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        long res = 0;
        do {
            if (c < '0' || c > '9') {
                throw new InputMismatchException();
            }
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }

    public int nextInt() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9') {
                throw new InputMismatchException();
            }
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }

    public boolean isSpaceChar(int c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public boolean isEndOfLine(int c) {
        return c == '\n' || c == '\r' || c == -1;
    }

    }
}