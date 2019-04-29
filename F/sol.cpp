#include <bits/stdc++.h>

using namespace std;

int lg2(unsigned long long x) {   
	return 64 - __builtin_clzll(x) - 1;	
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int tc;
	cin >> tc;

	while (tc--) {
		unsigned long long n;
		cin >> n;
		cout << 1 + 2 * (n - (1 << (lg2(n)))) << endl;
	}

	return 0;
}
