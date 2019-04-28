#include <bits/stdc++.h>
using namespace std;
#define pb push_back
#define mp make_pair
#define sz(x) ((int)(x).size())
#define all(x) (x).begin(), (x).end()
typedef long long ll;

const int mod = 1e9 + 7;
const int N = 5005;

int n;
string s;
int val[26];
int arr[N];
int dp[N][N];
int vis[N][N];

int solve(int i, int j)
{
  if (i > j)
    return 0;
  if (i == j)
    return max(arr[i], 0);
  int &r = dp[i][j];
  if (vis[i][j])
    return r;
  vis[i][j] = 1;
  r = 0;
  r = max(r, solve(i + 1, j));
  r = max(r, solve(i, j - 1));
  if (s[i] == s[j])
    r = max(r, 2 * arr[i] + solve(i + 1, j - 1));
  return r;
}

int main()
{
  int T;
  cin >> T;
  while (T--)
  {
    cin >> n;
    cin >> s;
    for (int i = 0; i < 26; i++)
      cin >> val[i];
    for (int i = 0; i < n; i++)
      arr[i] = val[s[i] - 'a'];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        vis[i][j] = 0;
    cout << solve(0, n - 1) << endl;
  }
  return 0;
}
